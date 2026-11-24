package es.uclm.esi.iso2.aparcamiento.excepciones;

import java.time.LocalDateTime;

/**
 * Se lanza cuando la fecha/hora de salida es anterior a la de entrada.
 */
public class SalidaAnteriorException extends EstanciaInvalidaException {

    private static final long serialVersionUID = 1L;

    public SalidaAnteriorException(LocalDateTime entrada, LocalDateTime salida) {
        super("La salida (" + salida + ") es anterior a la entrada (" + entrada + ")");
    }
}
