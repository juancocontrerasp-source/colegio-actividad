package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Centraliza la lectura de configuración y las conexiones JDBC a MySQL. */
public final class ConexionBD {
    private static final String ARCHIVO_CONFIGURACION = "config/database.properties";

    private ConexionBD() { }

    /** Abre y devuelve una conexión a la base de datos configurada. */
    public static Connection obtenerConexion() throws SQLException {
        Properties propiedades = new Properties();
        String rutaConfiguracion = System.getProperty("colegio.config", ARCHIVO_CONFIGURACION);
        try (var entrada = Files.newBufferedReader(Path.of(rutaConfiguracion), StandardCharsets.UTF_8)) {
            propiedades.load(entrada);
        } catch (IOException | RuntimeException error) {
            throw new SQLException("No se pudo leer " + rutaConfiguracion
                    + ". Ejecute el programa desde la carpeta proyectoColegio y revise el archivo.", error);
        }

        String url = propiedades.getProperty("db.url", "").trim();
        String usuario = propiedades.getProperty("db.user", "").trim();
        String contrasena = propiedades.getProperty("db.password", "");
        if (url.isEmpty() || usuario.isEmpty() || contrasena.isBlank()
                || "TU_CONTRASEÑA".equals(contrasena)) {
            throw new SQLException("Configure db.url, db.user y db.password en " + rutaConfiguracion
                    + " antes de iniciar la aplicación.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            throw new SQLException("No se encontró MySQL Connector/J. Revise que lib/mysql-connector-j-8.3.0.jar"
                    + " esté en el classpath.", error);
        }

        try {
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException error) {
            throw new SQLException("No fue posible conectarse a MySQL. Verifique que el servidor esté activo,"
                    + " que la base colegio_san_gabriel exista y que las credenciales sean correctas. Detalle: "
                    + error.getMessage(), error);
        }
    }
}