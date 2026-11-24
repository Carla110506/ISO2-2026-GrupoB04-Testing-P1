package es.uclm.esi.iso2.aparcamiento.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Reglas de negocio del aparcamiento:
 * <ul>
 *   <li>Los primeros 30 minutos de la estancia son gratuitos.</li>
 *   <li>A partir de ahí se cobra 0,05 EUR por minuto.</li>
 *   <li>Tope máximo de 20 EUR por cada periodo de 24 horas contado desde la entrada.</li>
 * </ul>
 * Los 30 minutos gratuitos se aplican una sola vez, al principio de la estancia.
 * Cada periodo completo de 24 horas cuesta el tope (20 EUR); el resto de minutos
 * se cobra por minuto, también con el tope de 20 EUR.
 */
public class TarifaEstandar implements CalculadoraTarifa {

    public static final long MINUTOS_GRATIS = 30;
    public static final long MINUTOS_POR_DIA = 24L * 60L;
    public static final BigDecimal PRECIO_MINUTO = new BigDecimal("0.05");
    public static final BigDecimal TOPE_DIARIO = new BigDecimal("20.00");

    @Override
    public BigDecimal calcularCoste(Estancia estancia) {
        long minutos = estancia.getMinutos();

        if (minutos <= MINUTOS_GRATIS) {
            return BigDecimal.ZERO.setScale(2);
        }

        long diasCompletos = minutos / MINUTOS_POR_DIA;
        long minutosResto = minutos % MINUTOS_POR_DIA;

        long minutosCobrablesResto;
        if (diasCompletos == 0) {
            minutosCobrablesResto = minutosResto - MINUTOS_GRATIS;
        } else {
            minutosCobrablesResto = minutosResto;
        }

        BigDecimal costeResto = PRECIO_MINUTO.multiply(BigDecimal.valueOf(minutosCobrablesResto));
        if (costeResto.compareTo(TOPE_DIARIO) > 0) {
            costeResto = TOPE_DIARIO;
        }

        BigDecimal costeDias = TOPE_DIARIO.multiply(BigDecimal.valueOf(diasCompletos));
        return costeDias.add(costeResto).setScale(2, RoundingMode.HALF_UP);
    }
}
