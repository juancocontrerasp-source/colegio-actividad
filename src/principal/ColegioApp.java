package principal;

import java.sql.Connection;
import java.sql.SQLException;
import repositorio.CitaRepositorio;
import repositorio.CoordinadorRepositorio;
import repositorio.EstudianteRepositorio;
import repositorio.ProfesorRepositorio;
import util.ConexionBD;
import util.EntradaConsola;
import vista.MenuColegio;

/** Inicia la aplicación después de comprobar el acceso a MySQL. */
public final class ColegioApp {
    private ColegioApp() { }

    /** Comprueba la conexión e inicia el menú si la base de datos responde. */
    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (!conexion.isValid(5)) {
                System.err.println("La conexión con MySQL no está disponible. Revise la configuración.");
                return;
            }
            System.out.println("Conexión con MySQL establecida correctamente.");
        } catch (SQLException error) {
            System.err.println("No se pudo iniciar el sistema. " + error.getMessage());
            return;
        }

        MenuColegio menu = new MenuColegio(new EstudianteRepositorio(), new ProfesorRepositorio(),
                new CoordinadorRepositorio(), new CitaRepositorio(), new EntradaConsola());
        menu.iniciar();
    }
}