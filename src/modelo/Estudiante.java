package modelo;

/** Representa a un estudiante del colegio. */
public class Estudiante {
    private int id;
    private String nombre;
    private int edad;
    private String documento;

    /** Crea un estudiante nuevo sin ID asignado por la base de datos. */
    public Estudiante(String nombre, int edad, String documento) {
        this(0, nombre, edad, documento);
    }

    /** Crea un estudiante con todos sus datos. */
    public Estudiante(int id, String nombre, int edad, String documento) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.documento = documento;
    }

    /** Devuelve el ID del estudiante. */
    public int getId() { return id; }
    /** Actualiza el ID del estudiante. */
    public void setId(int id) { this.id = id; }
    /** Devuelve el nombre del estudiante. */
    public String getNombre() { return nombre; }
    /** Actualiza el nombre del estudiante. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Devuelve la edad del estudiante. */
    public int getEdad() { return edad; }
    /** Actualiza la edad del estudiante. */
    public void setEdad(int edad) { this.edad = edad; }
    /** Devuelve el documento del estudiante. */
    public String getDocumento() { return documento; }
    /** Actualiza el documento del estudiante. */
    public void setDocumento(String documento) { this.documento = documento; }

    /** Muestra los datos principales del estudiante. */
    public void mostrarInformacion() {
        System.out.printf("ID: %d | Nombre: %s | Edad: %d | Documento: %s%n",
                id, nombre, edad, documento);
    }
}