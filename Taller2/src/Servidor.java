import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import logica.CapaLogica;

public class Servidor {

    public static void main(String[] args) {

        try {
            // 1) Crear registro RMI 
            LocateRegistry.createRegistry(1099);

            // 2) Crear fachada vacía
            CapaLogica fachada = new CapaLogica();

            // 3) Cargar datos desde archivo
            fachada.cargarDatos();

            // 4) Publicar objeto remoto
            Naming.rebind("rmi://localhost/Fachada", fachada);

            System.out.println("Servidor listo");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}