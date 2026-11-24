package es.uclm.esi.iso2.aparcamiento.excepciones;

/**
 * Se lanza cuando el texto introducido por teclado no es una fecha/hora válida.
 */
public class FormatoFechaInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public FormatoFechaInvalidoException(String texto, String formatoEsperado) {
        super("'" + texto + "' no es una fecha/hora válida. Formato esperado: " + formatoEsperado);
    }
}
