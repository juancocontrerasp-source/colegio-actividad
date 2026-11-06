package modelo;

/** Representa a un profesor y su asignatura. */
public class Profesor {
    private int id;
    private String nombre;
    private int edad;
    private String documento;
    private String asignatura;

    /** Crea un profesor nuevo sin ID asignado por la base de datos. */
    public Profesor(String nombre, int edad, String documento, String asignatura) {
        this(0, nombre, edad, documento, asignatura);
    }

    /** Crea un profesor con todos sus datos. */
    public Profesor(int id, String nombre, int edad, String documento, String asignatura) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.documento = documento;
        this.asignatura = asignatura;
    }

    /** Devuelve el ID del profesor. */
    public int getId() { return id; }
    /** Actualiza el ID del profesor. */
    public void setId(int id) { this.id = id; }
    /** Devuelve el nombre del profesor. */
    public String getNombre() { return nombre; }
    /** Actualiza el nombre del profesor. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Devuelve la edad del profesor. */
    public int getEdad() { return edad; }
    /** Actualiza la edad del profesor. */
    public void setEdad(int edad) { this.edad = edad; }
    /** Devuelve el documento del profesor. */
    public String getDocumento() { return documento; }
    /** Actualiza el documento del profesor. */
    public void setDocumento(String documento) { this.documento = documento; }
    /** Devuelve la asignatura del profesor. */
    public String getAsignatura() { return asignatura; }
    /** Actualiza la asignatura del profesor. */
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }

    /** Muestra los datos principales del profesor. */
    public void mostrarInformacion() {
        System.out.printf("ID: %d | Nombre: %s | Edad: %d | Documento: %s | Asignatura: %s%n",
                id, nombre, edad, documento, asignatura);
    }
}