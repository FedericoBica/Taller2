package grafica.controladores;

import grafica.ventanas.VentanaPostresDeVenta;
import logica.excepciones.VentaNoExisteException;
import logica.vo.VOItemOrden;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorPostresDeVenta extends ControladorBase {

    private VentanaPostresDeVenta ventana;

    public ControladorPostresDeVenta(VentanaPostresDeVenta ventana) {
        super();
        this.ventana = ventana;
    }

    public void buscarPostresDeVenta(int numVenta) {
        try {
            ArrayList<VOItemOrden> lista = fachada.listadoPostresDeVenta(numVenta);
            ventana.mostrarListado(lista);
        } catch (VentaNoExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}