package grafica.controladores;

import grafica.ventanas.VentanaEliminarPostreDeVenta;
import logica.excepciones.*;
import logica.vo.VOPostreVenta;
import java.rmi.RemoteException;

public class ControladorEliminarPostre extends ControladorBase {

    private VentanaEliminarPostreDeVenta ventana;

    public ControladorEliminarPostre(VentanaEliminarPostreDeVenta ventana) {
        super();
        this.ventana = ventana;
    }

    public void eliminarPostre(String codigo, int cantidad, int numVenta) {
        try {
            fachada.eliminarPostreDeVenta(new VOPostreVenta(codigo, cantidad, numVenta));
            ventana.mostrarExito("Postre eliminado correctamente.");
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