import java.rmi.Naming;
import logica.IFachada;

public class Cliente {

    public static void main(String[] args) {

        try {
            IFachada fachada =
                (IFachada) Naming.lookup(
                    "rmi://localhost/Fachada");

            System.out.println("Conectado al servidor");

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}