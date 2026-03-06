package grafica.controladores;

import grafica.ventanas.VentanaAgregarPostreVenta;
import logica.excepciones.*;
import logica.vo.VOPostreVenta;
import java.rmi.RemoteException;

public class ControladorAgregarPostreVenta extends ControladorBase {

    private VentanaAgregarPostreVenta ventana;

    public ControladorAgregarPostreVenta(VentanaAgregarPostreVenta ventana) {
        super();
        this.ventana = ventana;
    }

    public void agregarPostre(String codigo, int cantidad, int numVenta) {
        try {
            fachada.agregarPostreAVenta(new VOPostreVenta(codigo, cantidad, numVenta));
            ventana.mostrarExito("Postre agregado correctamente.");
            ventana.limpiarCampos();
        } catch (VentaNoExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (VentaYaFinalizadaException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (PostreNoExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (CantidadInvalidaException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}