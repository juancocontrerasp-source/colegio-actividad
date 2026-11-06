package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;
import util.ConexionBD;

/** Ejecuta las operaciones de persistencia para estudiantes. */
public class EstudianteRepositorio {
    /** Calcula el siguiente ID disponible como referencia informativa. */
    public int siguienteId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM estudiantes";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            resultado.next();
            return resultado.getInt(1);
        }
    }

    /** Inserta al estudiante y devuelve el ID asignado por MySQL. */
    public int guardar(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiantes (nombre, edad, documento) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement insercion = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insercion.setString(1, estudiante.getNombre());
            insercion.setInt(2, estudiante.getEdad());
            insercion.setString(3, estudiante.getDocumento());
            insercion.executeUpdate();
            try (ResultSet claves = insercion.getGeneratedKeys()) {
                if (!claves.next()) {
                    throw new SQLException("MySQL no devolvió el ID del estudiante registrado.");
                }
                estudiante.setId(claves.getInt(1));
                return estudiante.getId();
            }
        }
    }

    /** Devuelve todos los estudiantes ordenados por ID. */
    public List<Estudiante> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, edad, documento FROM estudiantes ORDER BY id";
        List<Estudiante> estudiantes = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                estudiantes.add(new Estudiante(resultado.getInt("id"), resultado.getString("nombre"),
                        resultado.getInt("edad"), resultado.getString("documento")));
            }
        }
        return estudiantes;
    }

    /** Busca un estudiante por su ID; devuelve null si no existe. */
    public Estudiante buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, edad, documento FROM estudiantes WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet resultado = consulta.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }
                return new Estudiante(resultado.getInt("id"), resultado.getString("nombre"),
                        resultado.getInt("edad"), resultado.getString("documento"));
            }
        }
    }
}