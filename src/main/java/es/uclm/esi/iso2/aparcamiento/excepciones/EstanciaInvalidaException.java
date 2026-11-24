package es.uclm.esi.iso2.aparcamiento.excepciones;

/**
 * Excepción base para cualquier estancia que no cumple las reglas de validez.
 */
public class EstanciaInvalidaException extends Exception {

    private static final long serialVersionUID = 1L;

    public EstanciaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
