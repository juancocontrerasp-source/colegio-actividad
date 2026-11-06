package util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/** Lee y valida los datos ingresados por teclado. */
public class EntradaConsola {
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    /** Crea un lector que usa la entrada estándar. */
    public EntradaConsola() {
        scanner = new Scanner(System.in);
    }

    /** Lee una línea de texto no vacía y vuelve a preguntar si hace falta. */
    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El valor no puede estar vacío.");
        }
    }

    /** Lee un entero dentro del rango indicado sin finalizar el programa ante errores. */
    public int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            try {
                int numero = Integer.parseInt(texto);
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException ignored) {
                // Se vuelve a solicitar el dato.
            }
            System.out.printf("Ingrese un número entero entre %d y %d.%n", minimo, maximo);
        }
    }

    /** Lee una fecha existente con formato AAAA-MM-DD. */
    public String leerFecha(String mensaje) {
        while (true) {
            String fecha = leerTexto(mensaje);
            try {
                return LocalDate.parse(fecha, FORMATO_FECHA).toString();
            } catch (DateTimeException error) {
                System.out.println("Fecha inválida. Use el formato AAAA-MM-DD.");
            }
        }
    }

    /** Lee una hora entre 00 y 23 con el formato HH solicitado. */
    public String leerHora(String mensaje) {
        while (true) {
            String hora = leerTexto(mensaje);
            if (hora.matches("(?:[01][0-9]|2[0-3])")) {
                return hora;
            }
            System.out.println("Hora inválida. Use el formato HH, desde 00 hasta 23.");
        }
    }
}