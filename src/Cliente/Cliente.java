package Cliente;

import javax.swing.SwingUtilities;
import grafica.ventanas.VentanaPrincipal;

public class Cliente {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            }
        });
    }
}