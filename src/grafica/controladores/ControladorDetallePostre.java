package grafica.controladores;

import grafica.ventanas.VentanaDetallePostre;
import logica.excepciones.PostreNoExisteException;
import logica.vo.VOPostreAlta;
import java.rmi.RemoteException;

public class ControladorDetallePostre extends ControladorBase {

    private VentanaDetallePostre ventana;

    public ControladorDetallePostre(VentanaDetallePostre ventana) {
        super();
        this.ventana = ventana;
    }

    public void buscarPostre(String codigo) {
        try {
            VOPostreAlta vo = fachada.listadoDetalladoPostre(codigo);
            ventana.mostrarDetalle(vo);
        } catch (PostreNoExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}