package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Coordinador;
import util.ConexionBD;

/** Ejecuta las operaciones de persistencia para coordinadores. */
public class CoordinadorRepositorio {
    /** Calcula el siguiente ID disponible como referencia informativa. */
    public int siguienteId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM coordinadores";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            resultado.next();
            return resultado.getInt(1);
        }
    }

    /** Inserta al coordinador y devuelve el ID asignado por MySQL. */
    public int guardar(Coordinador coordinador) throws SQLException {
        String sql = "INSERT INTO coordinadores (nombre, edad, documento) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement insercion = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insercion.setString(1, coordinador.getNombre());
            insercion.setInt(2, coordinador.getEdad());
            insercion.setString(3, coordinador.getDocumento());
            insercion.executeUpdate();
            try (ResultSet claves = insercion.getGeneratedKeys()) {
                if (!claves.next()) {
                    throw new SQLException("MySQL no devolvió el ID del coordinador registrado.");
                }
                coordinador.setId(claves.getInt(1));
                return coordinador.getId();
            }
        }
    }

    /** Devuelve todos los coordinadores ordenados por ID. */
    public List<Coordinador> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, edad, documento FROM coordinadores ORDER BY id";
        List<Coordinador> coordinadores = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                coordinadores.add(new Coordinador(resultado.getInt("id"), resultado.getString("nombre"),
                        resultado.getInt("edad"), resultado.getString("documento")));
            }
        }
        return coordinadores;
    }
}