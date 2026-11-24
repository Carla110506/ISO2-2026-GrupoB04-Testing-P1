package es.uclm.esi.iso2.aparcamiento.dominio;

import java.math.BigDecimal;

/**
 * Contrato para cualquier política de tarifas del aparcamiento.
 * Permite cambiar las reglas de negocio sin tocar el resto del programa
 * (principios abierto/cerrado e inversión de dependencias).
 */
public interface CalculadoraTarifa {

    BigDecimal calcularCoste(Estancia estancia);
}
