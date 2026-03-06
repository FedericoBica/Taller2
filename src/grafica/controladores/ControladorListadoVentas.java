package grafica.controladores;

import grafica.ventanas.VentanaListadoVentas;
import logica.vo.VOVenta;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorListadoVentas extends ControladorBase {

    private VentanaListadoVentas ventana;

    public ControladorListadoVentas(VentanaListadoVentas ventana) {
        super();
        this.ventana = ventana;
    }

    public void cargarVentas(char tipo) {
        try {
            ArrayList<VOVenta> lista = fachada.listadoVentas(tipo);
            ventana.mostrarListado(lista);
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}