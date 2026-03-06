package grafica.controladores;

import grafica.ventanas.VentanaPrincipal;
import logica.excepciones.PersistenciaException;
import java.rmi.RemoteException;

public class ControladorPrincipal extends ControladorBase {

    private VentanaPrincipal ventana;

    public ControladorPrincipal(VentanaPrincipal ventana) {
        super();
        this.ventana = ventana;
    }

    public void respaldarDatos() {
        try {
            fachada.respaldarDatos();
            ventana.mostrarExito("Datos respaldados correctamente.");
        } catch (PersistenciaException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }

    public void recuperarDatos() {
        try {
            fachada.recuperarDatos();
            ventana.mostrarExito("Datos recuperados correctamente.");
        } catch (PersistenciaException ex) {
            ventana.mostrarError(ex.darMensaje());
        } catch (RemoteException ex) {
            ventana.mostrarError("Error de conexion: " + ex.getMessage());
        }
    }
}