package es.uclm.esi.iso2.aparcamiento.ui;

import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * Escribe en pantalla. Recibe el PrintStream por constructor para poder
 * probarlo capturando la salida.
 */
public class PantallaConsola {

    private final PrintStream salida;

    public PantallaConsola(PrintStream salida) {
        this.salida = salida;
    }

    public void mostrarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    public void pedir(String mensaje) {
        salida.print(mensaje);
        salida.flush();
    }

    public void mostrarCoste(long minutos, BigDecimal coste) {
        salida.println("Duración de la estancia: " + minutos + " minutos");
        salida.println("Coste total: " + coste.toPlainString().replace('.', ',') + " EUR");
    }

    public void mostrarError(String mensaje) {
        salida.println("ERROR: " + mensaje);
    }
}
