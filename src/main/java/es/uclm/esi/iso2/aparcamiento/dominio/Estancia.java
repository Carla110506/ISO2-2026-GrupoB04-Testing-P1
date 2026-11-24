package es.uclm.esi.iso2.aparcamiento.dominio;

import java.time.Duration;
import java.time.LocalDateTime;

import es.uclm.esi.iso2.aparcamiento.excepciones.EstanciaInvalidaException;
import es.uclm.esi.iso2.aparcamiento.excepciones.FechaNulaException;
import es.uclm.esi.iso2.aparcamiento.excepciones.SalidaAnteriorException;

/**
 * Estancia de un vehículo en el aparcamiento (ticket de entrada y de salida).
 *
 * Las comprobaciones de validez se hacen en el constructor: si un objeto
 * Estancia existe, es válido. Los atributos son privados y finales
 * (encapsulamiento e inmutabilidad).
 */
public class Estancia {

    private final LocalDateTime entrada;
    private final LocalDateTime salida;

    public Estancia(LocalDateTime entrada, LocalDateTime salida) throws EstanciaInvalidaException {
        if (entrada == null || salida == null) {
            throw new FechaNulaException(entrada == null ? "entrada" : "salida");
        }
        if (salida.isBefore(entrada)) {
            throw new SalidaAnteriorException(entrada, salida);
        }
        this.entrada = entrada;
        this.salida = salida;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSalida() {
        return salida;
    }

    /**
     * Duración de la estancia en minutos. Cualquier fracción de minuto
     * empezada cuenta como minuto completo (redondeo hacia arriba).
     */
    public long getMinutos() {
        long segundos = Duration.between(entrada, salida).getSeconds();
        return (segundos + 59) / 60;
    }
}
