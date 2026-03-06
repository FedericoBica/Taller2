package Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import logica.IFachada;
import grafica.ventanas.VentanaPrincipal;

public class Cliente {

    public static void main(String[] args) {

        Properties props = new Properties();
        try (InputStream is =
                Cliente.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            System.err.println("No se pudo leer config.properties: " + e.getMessage());
        }

        String ip = props.getProperty("ipServidor",    "127.0.0.1");
        int puerto = Integer.parseInt(props.getProperty("puertoServidor", "1099"));

        try {
            String url = "rmi://" + ip + ":" + puerto + "/Fachada";
            IFachada fachada = (IFachada) Naming.lookup(url);
            System.out.println("Conectado al servidor en " + url);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    VentanaPrincipal v = new VentanaPrincipal(fachada);
                    v.setVisible(true);
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "No se pudo conectar al servidor RMI.\n" + e.getMessage(),
                "Error de conexion", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}