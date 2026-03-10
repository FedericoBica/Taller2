package grafica.controladores;

import grafica.ventanas.VentanaComenzarVenta;
import logica.vo.VOVentaAlta;
import logica.excepciones.FechaInvalidaException;
import java.rmi.RemoteException;
import java.time.LocalDate;

public class ControladorComenzarVenta extends ControladorBase {

    private VentanaComenzarVenta ventana;

    public ControladorComenzarVenta(VentanaComenzarVenta ventana) {
        super();
        this.ventana = ventana;
    }

    public void comenzarVenta(LocalDate fecha, String direccion) {
        try {
        	VOVentaAlta vo = new VOVentaAlta(fecha, direccion);
            fachada.comenzarVenta(vo);
            ventana.mostrarExito("Venta iniciada correctamente.");
            ventana.cerrar();
        } catch (FechaInvalidaException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}