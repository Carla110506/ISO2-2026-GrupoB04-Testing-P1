package es.uclm.esi.iso2.aparcamiento.excepciones;

/**
 * Se lanza cuando la fecha/hora de entrada o de salida es nula.
 */
public class FechaNulaException extends EstanciaInvalidaException {

    private static final long serialVersionUID = 1L;

    public FechaNulaException(String campo) {
        super("La fecha/hora de " + campo + " no puede ser nula");
    }
}
