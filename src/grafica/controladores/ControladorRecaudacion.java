package grafica.controladores;

import grafica.ventanas.VentanaRecaudacion;
import logica.excepciones.PostreNoExisteException;
import logica.vo.VORecaudacion;
import java.rmi.RemoteException;
import java.time.LocalDate;

public class ControladorRecaudacion extends ControladorBase {

    private VentanaRecaudacion ventana;

    public ControladorRecaudacion(VentanaRecaudacion ventana) {
        super();
        this.ventana = ventana;
    }

    public void consultarRecaudacion(String codigo, LocalDate fecha) {
        try {
            VORecaudacion rec = fachada.totalVentasPostreEnFecha(codigo, fecha);
            ventana.mostrarRecaudacion(rec);
        } catch (PostreNoExisteException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}