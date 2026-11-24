package es.uclm.esi.iso2.aparcamiento.ui;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.NoSuchElementException;
import java.util.Scanner;

import es.uclm.esi.iso2.aparcamiento.excepciones.FormatoFechaInvalidoException;

/**
 * Lee datos de teclado. Recibe el InputStream por constructor para poder
 * probarlo sin teclado real (por ejemplo con un ByteArrayInputStream).
 */
public class LectorConsola {

    /** Formato que se muestra al usuario. */
    public static final String FORMATO = "dd/MM/aaaa HH:mm";
    private static final DateTimeFormatter FORMATEADOR =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private final Scanner scanner;

    public LectorConsola(InputStream entrada) {
        this.scanner = new Scanner(entrada);
    }

    /** Devuelve la siguiente línea, o null si ya no hay más entrada. */
    public String leerLinea() {
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /** Convierte un texto con formato dd/MM/yyyy HH:mm en fecha/hora. */
    public LocalDateTime parsearFecha(String texto) throws FormatoFechaInvalidoException {
        if (texto == null || texto.isBlank()) {
            throw new FormatoFechaInvalidoException(String.valueOf(texto), FORMATO);
        }
        try {
            return LocalDateTime.parse(texto.trim(), FORMATEADOR);
        } catch (DateTimeParseException e) {
            throw new FormatoFechaInvalidoException(texto, FORMATO);
        }
    }
}
