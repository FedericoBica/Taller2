package grafica.controladores;

import grafica.ventanas.VentanaFinalizarVenta;
import logica.vo.VOFinalizacion;
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
        	VOFinalizacion vo = new VOFinalizacion(numVenta, confirmar);
            float monto = fachada.finalizarVenta(vo);
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