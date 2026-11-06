package modelo;

/** Representa una cita de tutoría; los nombres asociados son solo para mostrar JOINs. */
public class Cita {
    private int id;
    private int estudianteId;
    private int profesorId;
    private String fecha;
    private String hora;
    private String estado;
    private String nombreEstudiante;
    private String nombreProfesor;

    /** Crea una cita nueva cuyo estado inicial es PROGRAMADA. */
    public Cita(int estudianteId, int profesorId, String fecha, String hora) {
        this(0, estudianteId, profesorId, fecha, hora, "PROGRAMADA", null, null);
    }

    /** Crea una cita con todos sus datos, incluidos nombres opcionales para presentación. */
    public Cita(int id, int estudianteId, int profesorId, String fecha, String hora,
            String estado, String nombreEstudiante, String nombreProfesor) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.profesorId = profesorId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.nombreEstudiante = nombreEstudiante;
        this.nombreProfesor = nombreProfesor;
    }

    /** Devuelve el ID de la cita. */
    public int getId() { return id; }
    /** Actualiza el ID de la cita. */
    public void setId(int id) { this.id = id; }
    /** Devuelve el ID del estudiante relacionado. */
    public int getEstudianteId() { return estudianteId; }
    /** Actualiza el ID del estudiante relacionado. */
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }
    /** Devuelve el ID del profesor relacionado. */
    public int getProfesorId() { return profesorId; }
    /** Actualiza el ID del profesor relacionado. */
    public void setProfesorId(int profesorId) { this.profesorId = profesorId; }
    /** Devuelve la fecha de la cita. */
    public String getFecha() { return fecha; }
    /** Actualiza la fecha de la cita. */
    public void setFecha(String fecha) { this.fecha = fecha; }
    /** Devuelve la hora de la cita. */
    public String getHora() { return hora; }
    /** Actualiza la hora de la cita. */
    public void setHora(String hora) { this.hora = hora; }
    /** Devuelve el estado de la cita. */
    public String getEstado() { return estado; }
    /** Actualiza el estado de la cita. */
    public void setEstado(String estado) { this.estado = estado; }
    /** Devuelve el nombre del estudiante unido desde la base de datos. */
    public String getNombreEstudiante() { return nombreEstudiante; }
    /** Actualiza el nombre del estudiante usado para mostrar la cita. */
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }
    /** Devuelve el nombre del profesor unido desde la base de datos. */
    public String getNombreProfesor() { return nombreProfesor; }
    /** Actualiza el nombre del profesor usado para mostrar la cita. */
    public void setNombreProfesor(String nombreProfesor) { this.nombreProfesor = nombreProfesor; }

    /** Imprime un resumen usando los nombres recibidos. */
    public void mostrarResumen(String nombreEstudiante, String nombreProfesor) {
        System.out.printf("ID: %d | Estudiante: %s | Profesor: %s | Fecha: %s | Hora: %s | Estado: %s%n",
                id, nombreEstudiante, nombreProfesor, fecha, hora, estado);
    }
}