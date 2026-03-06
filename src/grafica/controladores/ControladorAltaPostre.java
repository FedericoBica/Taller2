package grafica.controladores;

import grafica.ventanas.VentanaAltaPostre;
import logica.excepciones.*;
import logica.vo.VOPostreAlta;
import java.rmi.RemoteException;

public class ControladorAltaPostre extends ControladorBase {

    private VentanaAltaPostre ventana;

    public ControladorAltaPostre(VentanaAltaPostre ventana) {
        super();
        this.ventana = ventana;
    }

    public void altaPostre(String codigo, String nombre, float precio,
                           boolean esLight, String endulzante, String descripcion) {
        try {
            VOPostreAlta vo;
            if (esLight) {
                vo = new VOPostreAlta(codigo, nombre, precio, "LIGHT", endulzante, descripcion);
            } else {
                vo = new VOPostreAlta(codigo, nombre, precio, "NORMAL");
            }
            fachada.altaPostre(vo);
            ventana.mostrarExito("Postre dado de alta correctamente");
            ventana.limpiarCampos();
        } catch (PostreYaExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (CodigoInvalidoException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (PrecioInvalidoException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}