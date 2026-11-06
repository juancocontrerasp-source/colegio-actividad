package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Profesor;
import util.ConexionBD;

/** Ejecuta las operaciones de persistencia para profesores. */
public class ProfesorRepositorio {
    /** Calcula el siguiente ID disponible como referencia informativa. */
    public int siguienteId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM profesores";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            resultado.next();
            return resultado.getInt(1);
        }
    }

    /** Inserta al profesor y devuelve el ID asignado por MySQL. */
    public int guardar(Profesor profesor) throws SQLException {
        String sql = "INSERT INTO profesores (nombre, edad, documento, asignatura) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement insercion = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insercion.setString(1, profesor.getNombre());
            insercion.setInt(2, profesor.getEdad());
            insercion.setString(3, profesor.getDocumento());
            insercion.setString(4, profesor.getAsignatura());
            insercion.executeUpdate();
            try (ResultSet claves = insercion.getGeneratedKeys()) {
                if (!claves.next()) {
                    throw new SQLException("MySQL no devolvió el ID del profesor registrado.");
                }
                profesor.setId(claves.getInt(1));
                return profesor.getId();
            }
        }
    }

    /** Devuelve todos los profesores ordenados por ID. */
    public List<Profesor> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, edad, documento, asignatura FROM profesores ORDER BY id";
        List<Profesor> profesores = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                profesores.add(new Profesor(resultado.getInt("id"), resultado.getString("nombre"),
                        resultado.getInt("edad"), resultado.getString("documento"),
                        resultado.getString("asignatura")));
            }
        }
        return profesores;
    }

    /** Busca un profesor por su ID; devuelve null si no existe. */
    public Profesor buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, edad, documento, asignatura FROM profesores WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet resultado = consulta.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }
                return new Profesor(resultado.getInt("id"), resultado.getString("nombre"),
                        resultado.getInt("edad"), resultado.getString("documento"),
                        resultado.getString("asignatura"));
            }
        }
    }
}