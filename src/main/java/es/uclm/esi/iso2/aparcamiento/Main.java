package es.uclm.esi.iso2.aparcamiento;

import es.uclm.esi.iso2.aparcamiento.dominio.TarifaEstandar;
import es.uclm.esi.iso2.aparcamiento.ui.ControladorAparcamiento;
import es.uclm.esi.iso2.aparcamiento.ui.LectorConsola;
import es.uclm.esi.iso2.aparcamiento.ui.PantallaConsola;

/**
 * Punto de entrada: monta las piezas y arranca el programa.
 */
public class Main {

    public static void main(String[] args) {
        LectorConsola lector = new LectorConsola(System.in);
        PantallaConsola pantalla = new PantallaConsola(System.out);
        ControladorAparcamiento controlador =
                new ControladorAparcamiento(lector, pantalla, new TarifaEstandar());
        controlador.ejecutar();
    }
}
