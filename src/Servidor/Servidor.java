package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;
import logica.CapaLogica;

public class Servidor {

    public static void main(String[] args) {
    	
    	 Properties props = new Properties();
         try (InputStream is =
                  Servidor.class.getClassLoader().getResourceAsStream("config.properties")) {
             if (is != null) props.load(is);
         } catch (IOException e) {
             System.err.println("No se pudo leer config.properties: " + e.getMessage());
         }

         String ip     = props.getProperty("ipServidor",    "127.0.0.1");
         int    puerto = Integer.parseInt(props.getProperty("puertoServidor", "1099"));


        try {
            // 1) Crear registro RMI 
            LocateRegistry.createRegistry(puerto);

            // 2) Crear fachada vacía
            CapaLogica fachada = new CapaLogica();

            // 3) Cargar datos desde archivo
            fachada.cargarDatos();

            String url = "rmi://" + ip + ":" + puerto + "/Fachada";
            Naming.rebind(url, fachada);

            System.out.println("Servidor listo");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}