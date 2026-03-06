package grafica.controladores;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.util.Properties;
import logica.IFachada;

public abstract class ControladorBase {

    protected IFachada fachada;

    public ControladorBase() {
        this.fachada = conectar();
    }

    private IFachada conectar() {
        Properties props = new Properties();
        try (InputStream is =
                ControladorBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            System.err.println("No se pudo leer config.properties: " + e.getMessage());
        }
        String ip    = props.getProperty("ipServidor",    "127.0.0.1");
        int    puerto = Integer.parseInt(props.getProperty("puertoServidor", "1099"));
        try {
            String url = "rmi://" + ip + ":" + puerto + "/Fachada";
            return (IFachada) Naming.lookup(url);
        } catch (Exception e) {
            System.err.println("Error al conectar con el servidor RMI: " + e.getMessage());
            return null;
        }
    }
}