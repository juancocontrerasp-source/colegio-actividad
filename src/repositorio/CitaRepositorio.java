package repositorio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelo.Cita;
import util.ConexionBD;

/** Ejecuta las operaciones de persistencia para citas de tutoría. */
public class CitaRepositorio {
    private static final String CONSULTA_CON_NOMBRES = "SELECT c.id, c.estudiante_id, c.profesor_id, "
            + "c.fecha, c.hora, c.estado, e.nombre AS nombre_estudiante, p.nombre AS nombre_profesor "
            + "FROM citas c INNER JOIN estudiantes e ON c.estudiante_id = e.id "
            + "INNER JOIN profesores p ON c.profesor_id = p.id ";

    /** Calcula el siguiente ID disponible como referencia informativa. */
    public int siguienteId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM citas";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            resultado.next();
            return resultado.getInt(1);
        }
    }

    /** Guarda una cita con estado PROGRAMADA y devuelve su ID generado. */
    public int guardar(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (estudiante_id, profesor_id, fecha, hora, estado) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement insercion = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insercion.setInt(1, cita.getEstudianteId());
            insercion.setInt(2, cita.getProfesorId());
            insercion.setDate(3, Date.valueOf(LocalDate.parse(cita.getFecha())));
            int hora = Integer.parseInt(cita.getHora());
            insercion.setTime(4, Time.valueOf(LocalTime.of(hora, 0)));
            insercion.setString(5, "PROGRAMADA");
            insercion.executeUpdate();
            try (ResultSet claves = insercion.getGeneratedKeys()) {
                if (!claves.next()) {
                    throw new SQLException("MySQL no devolvió el ID de la cita creada.");
                }
                cita.setId(claves.getInt(1));
                cita.setEstado("PROGRAMADA");
                return cita.getId();
            }
        }
    }

    /** Devuelve todas las citas junto con los nombres de estudiante y profesor. */
    public List<Cita> listarTodos() throws SQLException {
        String sql = CONSULTA_CON_NOMBRES + "ORDER BY c.fecha, c.hora, c.id";
        List<Cita> citas = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                citas.add(mapearCita(resultado));
            }
        }
        return citas;
    }

    /** Busca una cita por ID, incluyendo los nombres asociados; devuelve null si no existe. */
    public Cita buscarPorId(int id) throws SQLException {
        String sql = CONSULTA_CON_NOMBRES + "WHERE c.id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet resultado = consulta.executeQuery()) {
                return resultado.next() ? mapearCita(resultado) : null;
            }
        }
    }

    /** Cambia el estado a CANCELADA sin borrar la fila y señala si encontró la cita. */
    public boolean cancelar(int id) throws SQLException {
        String sql = "UPDATE citas SET estado = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement actualizacion = conexion.prepareStatement(sql)) {
            actualizacion.setString(1, "CANCELADA");
            actualizacion.setInt(2, id);
            return actualizacion.executeUpdate() > 0;
        }
    }

    /** Convierte una fila del JOIN en un objeto de dominio. */
    private Cita mapearCita(ResultSet resultado) throws SQLException {
        return new Cita(resultado.getInt("id"), resultado.getInt("estudiante_id"),
                resultado.getInt("profesor_id"), resultado.getDate("fecha").toString(),
                resultado.getTime("hora").toLocalTime().format(DateTimeFormatter.ofPattern("HH")),
                resultado.getString("estado"), resultado.getString("nombre_estudiante"),
                resultado.getString("nombre_profesor"));
    }
}