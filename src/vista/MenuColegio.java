package vista;

import java.sql.SQLException;
import java.util.List;
import modelo.Cita;
import modelo.Coordinador;
import modelo.Estudiante;
import modelo.Profesor;
import repositorio.CitaRepositorio;
import repositorio.CoordinadorRepositorio;
import repositorio.EstudianteRepositorio;
import repositorio.ProfesorRepositorio;
import util.EntradaConsola;

/** Presenta el menú y coordina las acciones del usuario con los repositorios. */
public class MenuColegio {
    private final EstudianteRepositorio estudianteRepositorio;
    private final ProfesorRepositorio profesorRepositorio;
    private final CoordinadorRepositorio coordinadorRepositorio;
    private final CitaRepositorio citaRepositorio;
    private final EntradaConsola entrada;

    /** Crea el menú con sus dependencias ya configuradas. */
    public MenuColegio(EstudianteRepositorio estudianteRepositorio,
            ProfesorRepositorio profesorRepositorio, CoordinadorRepositorio coordinadorRepositorio,
            CitaRepositorio citaRepositorio, EntradaConsola entrada) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.profesorRepositorio = profesorRepositorio;
        this.coordinadorRepositorio = coordinadorRepositorio;
        this.citaRepositorio = citaRepositorio;
        this.entrada = entrada;
    }

    /** Mantiene el menú activo hasta que el usuario seleccione salir. */
    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = entrada.leerEntero("Seleccione una opción: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
            try {
                continuar = procesarOpcion(opcion);
            } catch (SQLException error) {
                System.out.println("No se pudo completar la operación: " + error.getMessage());
            }
        }
    }

    /** Imprime las opciones disponibles en consola. */
    private void mostrarMenu() {
        System.out.println("\n===== COLEGIO SAN GABRIEL =====");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Registrar profesor");
        System.out.println("3. Registrar coordinador");
        System.out.println("4. Listar estudiantes");
        System.out.println("5. Listar profesores");
        System.out.println("6. Crear cita de tutoria");
        System.out.println("7. Ver citas");
        System.out.println("8. Cancelar cita");
        System.out.println("9. Salir");
        System.out.println("========================================");
    }

    /** Ejecuta la opción seleccionada y devuelve si el menú debe continuar. */
    private boolean procesarOpcion(int opcion) throws SQLException {
        switch (opcion) {
            case 0, 9 -> {
                System.out.println("Gracias por utilizar el sistema del Colegio San Gabriel. ¡Hasta pronto!");
                return false;
            }
            case 1 -> registrarEstudiante();
            case 2 -> registrarProfesor();
            case 3 -> registrarCoordinador();
            case 4 -> listarEstudiantes();
            case 5 -> listarProfesores();
            case 6 -> crearCita();
            case 7 -> listarCitas();
            case 8 -> cancelarCita();
            default -> {
                System.out.println("Opcion invalida.");
            }
        }
        return true;
    }

    /** Solicita y guarda un estudiante. */
    private void registrarEstudiante() throws SQLException {
        String nombre = entrada.leerTexto("Nombre: ");
        int edad = entrada.leerEntero("Edad: ", 1, 120);
        String documento = entrada.leerTexto("Documento: ");
        Estudiante estudiante = new Estudiante(nombre, edad, documento);
        System.out.println("Estudiante registrado con ID: " + estudianteRepositorio.guardar(estudiante));
    }

    /** Solicita y guarda un profesor. */
    private void registrarProfesor() throws SQLException {
        String nombre = entrada.leerTexto("Nombre: ");
        int edad = entrada.leerEntero("Edad: ", 1, 120);
        String documento = entrada.leerTexto("Documento: ");
        String asignatura = entrada.leerTexto("Asignatura: ");
        Profesor profesor = new Profesor(nombre, edad, documento, asignatura);
        System.out.println("Profesor registrado con ID: " + profesorRepositorio.guardar(profesor));
    }

    /** Solicita y guarda un coordinador. */
    private void registrarCoordinador() throws SQLException {
        String nombre = entrada.leerTexto("Nombre: ");
        int edad = entrada.leerEntero("Edad: ", 1, 120);
        String documento = entrada.leerTexto("Documento: ");
        Coordinador coordinador = new Coordinador(nombre, edad, documento);
        System.out.println("Coordinador registrado con ID: " + coordinadorRepositorio.guardar(coordinador));
    }

    /** Consulta y muestra todos los estudiantes. */
    private void listarEstudiantes() throws SQLException {
        List<Estudiante> estudiantes = estudianteRepositorio.listarTodos();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        estudiantes.forEach(Estudiante::mostrarInformacion);
    }

    /** Consulta y muestra todos los profesores. */
    private void listarProfesores() throws SQLException {
        List<Profesor> profesores = profesorRepositorio.listarTodos();
        if (profesores.isEmpty()) {
            System.out.println("No hay profesores registrados.");
            return;
        }
        profesores.forEach(Profesor::mostrarInformacion);
    }

    /** Valida las dos llaves foráneas antes de guardar una cita. */
    private void crearCita() throws SQLException {
        int estudianteId = entrada.leerEntero("ID del estudiante: ", 1, Integer.MAX_VALUE);
        Estudiante estudiante = estudianteRepositorio.buscarPorId(estudianteId);
        if (estudiante == null) {
            System.out.println("No existe un estudiante con ese ID. No se creó la cita.");
            return;
        }

        int profesorId = entrada.leerEntero("ID del profesor: ", 1, Integer.MAX_VALUE);
        Profesor profesor = profesorRepositorio.buscarPorId(profesorId);
        if (profesor == null) {
            System.out.println("No existe un profesor con ese ID. No se creó la cita.");
            return;
        }

        String fecha = entrada.leerFecha("Fecha (AAAA-MM-DD): ");
        String hora = entrada.leerHora("Hora (HH): ");
        Cita cita = new Cita(estudianteId, profesorId, fecha, hora);
        int id = citaRepositorio.guardar(cita);
        System.out.println("Cita creada con ID: " + id + " | Estado: PROGRAMADA");
    }

    /** Consulta y muestra las citas con nombres obtenidos desde el JOIN. */
    private void listarCitas() throws SQLException {
        List<Cita> citas = citaRepositorio.listarTodos();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        citas.forEach(cita -> cita.mostrarResumen(cita.getNombreEstudiante(), cita.getNombreProfesor()));
    }

    /** Cancela una cita cambiando su estado, sin eliminar su registro. */
    private void cancelarCita() throws SQLException {
        int id = entrada.leerEntero("ID de la cita que desea cancelar: ", 1, Integer.MAX_VALUE);
        if (citaRepositorio.cancelar(id)) {
            System.out.println("La cita " + id + " ahora tiene estado CANCELADA.");
        } else {
            Cita cita = citaRepositorio.buscarPorId(id);
            if (cita == null) {
                System.out.println("No existe una cita con ese ID.");
            } else {
                System.out.println("La cita " + id + " ya tenía estado " + cita.getEstado() + ".");
            }
        }
    }
}