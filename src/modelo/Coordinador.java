package modelo;

/** Representa a un coordinador del colegio. */
public class Coordinador {
    private int id;
    private String nombre;
    private int edad;
    private String documento;

    /** Crea un coordinador nuevo sin ID asignado por la base de datos. */
    public Coordinador(String nombre, int edad, String documento) {
        this(0, nombre, edad, documento);
    }

    /** Crea un coordinador con todos sus datos. */
    public Coordinador(int id, String nombre, int edad, String documento) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.documento = documento;
    }

    /** Devuelve el ID del coordinador. */
    public int getId() { return id; }
    /** Actualiza el ID del coordinador. */
    public void setId(int id) { this.id = id; }
    /** Devuelve el nombre del coordinador. */
    public String getNombre() { return nombre; }
    /** Actualiza el nombre del coordinador. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Devuelve la edad del coordinador. */
    public int getEdad() { return edad; }
    /** Actualiza la edad del coordinador. */
    public void setEdad(int edad) { this.edad = edad; }
    /** Devuelve el documento del coordinador. */
    public String getDocumento() { return documento; }
    /** Actualiza el documento del coordinador. */
    public void setDocumento(String documento) { this.documento = documento; }

    /** Muestra los datos principales del coordinador. */
    public void mostrarInformacion() {
        System.out.printf("ID: %d | Nombre: %s | Edad: %d | Documento: %s%n",
                id, nombre, edad, documento);
    }
}