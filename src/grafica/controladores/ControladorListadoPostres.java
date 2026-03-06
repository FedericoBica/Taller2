package grafica.controladores;

import grafica.ventanas.VentanaListadoPostres;
import logica.vo.VOPostreListado;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorListadoPostres extends ControladorBase {

    private VentanaListadoPostres ventana;

    public ControladorListadoPostres(VentanaListadoPostres ventana) {
        super();
        this.ventana = ventana;
    }

    public void cargarListado() {
        try {
            ArrayList<VOPostreListado> lista = fachada.listadoGeneralPostres();
            ventana.mostrarListado(lista);
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}