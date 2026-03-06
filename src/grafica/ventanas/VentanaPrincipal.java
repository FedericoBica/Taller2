package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import logica.IFachada;

public class VentanaPrincipal {

    private JFrame frame;
    private IFachada fachada;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
		frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Sistema de Postres");
        frame.setResizable(false);
        frame.setBounds(100, 100, 320, 490);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema de Postres");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(65, 10, 210, 25);
        panel.add(lblTitulo);

        String[] textos = {
            "1. Alta de Postre",
            "2. Listado General de Postres",
            "3. Detalle de Postre",
            "4. Comenzar Venta",
            "5. Agregar Postre a Venta",
            "6. Eliminar Postre de Venta",
            "7. Finalizar Venta",
            "8. Listado de Ventas",
            "9. Postres de una Venta",
            "10. Recaudacion por Postre/Fecha",
            "11. Respaldar Datos",
            "12. Recuperar Datos"
        };
     
    }

    private void abrirVentana(int req) {
        switch (req) {
            case 1:  { VentanaAltaPostre v = new VentanaAltaPostre(fachada); v.setVisible(true); break; }
            case 2:  { VentanaListadoPostres v = new VentanaListadoPostres(fachada); v.setVisible(true); break; }

        }
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public VentanaPrincipal(IFachada fachada) {
        this.fachada = fachada;
        this.initialize();
        this.setVisible(false);
    }
}
