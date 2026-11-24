package es.uclm.esi.iso2.aparcamiento.ui;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import es.uclm.esi.iso2.aparcamiento.dominio.CalculadoraTarifa;
import es.uclm.esi.iso2.aparcamiento.dominio.Estancia;
import es.uclm.esi.iso2.aparcamiento.excepciones.EstanciaInvalidaException;
import es.uclm.esi.iso2.aparcamiento.excepciones.FormatoFechaInvalidoException;

/**
 * Coordina la interfaz de consola con la lógica de dominio.
 * El dominio no conoce esta clase: la dependencia va solo de la UI al dominio.
 */
public class ControladorAparcamiento {

    private final LectorConsola lector;
    private final PantallaConsola pantalla;
    private final CalculadoraTarifa tarifa;

    public ControladorAparcamiento(LectorConsola lector, PantallaConsola pantalla, CalculadoraTarifa tarifa) {
        this.lector = lector;
        this.pantalla = pantalla;
        this.tarifa = tarifa;
    }

    /** Bucle principal: calcula estancias hasta que el usuario escribe "salir". */
    public void ejecutar() {
        pantalla.mostrarMensaje("=== Cálculo del coste de aparcamiento ===");
        pantalla.mostrarMensaje("Formato de fecha: " + LectorConsola.FORMATO + " (escribe 'salir' para terminar)");

        while (true) {
            LocalDateTime entrada;
            LocalDateTime salida;
            try {
                entrada = pedirFecha("Fecha/hora de entrada: ");
                salida = pedirFecha("Fecha/hora de salida:  ");
            } catch (FinEntradaException e) {
                pantalla.mostrarMensaje("Hasta luego.");
                return;
            }
            procesar(entrada, salida);
        }
    }

    /** Calcula y muestra el coste; muestra el error si la estancia no es válida. */
    public void procesar(LocalDateTime entrada, LocalDateTime salida) {
        try {
            Estancia estancia = new Estancia(entrada, salida);
            BigDecimal coste = tarifa.calcularCoste(estancia);
            pantalla.mostrarCoste(estancia.getMinutos(), coste);
        } catch (EstanciaInvalidaException e) {
            pantalla.mostrarError(e.getMessage());
        }
    }

    private LocalDateTime pedirFecha(String mensaje) throws FinEntradaException {
        while (true) {
            pantalla.pedir(mensaje);
            String linea = lector.leerLinea();
            if (linea == null || linea.equalsIgnoreCase("salir")) {
                throw new FinEntradaException();
            }
            try {
                return lector.parsearFecha(linea);
            } catch (FormatoFechaInvalidoException e) {
                pantalla.mostrarError(e.getMessage());
            }
        }
    }

    /** Señala que el usuario quiere terminar (o que no hay más entrada). */
    private static class FinEntradaException extends Exception {
        private static final long serialVersionUID = 1L;
    }
}
