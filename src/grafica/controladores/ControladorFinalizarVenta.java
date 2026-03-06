package grafica.controladores;

import grafica.ventanas.VentanaFinalizarVenta;
import logica.excepciones.*;
import java.rmi.RemoteException;

public class ControladorFinalizarVenta extends ControladorBase {

    private VentanaFinalizarVenta ventana;

    public ControladorFinalizarVenta(VentanaFinalizarVenta ventana) {
        super();
        this.ventana = ventana;
    }

    public void finalizarVenta(int numVenta, boolean confirmar) {
        try {
            float monto = fachada.finalizarVenta(numVenta, confirmar);
            if (confirmar) {
                ventana.mostrarExito(String.format("Venta finalizada. Monto total: $ %.2f", monto));
            } else {
                ventana.mostrarExito("Venta cancelada y eliminada.");
            }
            ventana.cerrar();
        } catch (VentaNoExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (VentaYaFinalizadaException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}