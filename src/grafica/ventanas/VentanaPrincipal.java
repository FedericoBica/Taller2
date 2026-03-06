package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import grafica.controladores.ControladorPrincipal;

public class VentanaPrincipal {

    private JFrame frame;
    private ControladorPrincipal controlador;

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

        for (int i = 0; i < textos.length; i++) {
            final int req = i + 1;
            JButton btn = new JButton(textos[i]);
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            btn.setBackground(new Color(173, 216, 230));
            btn.setBounds(30, 45 + i * 33, 250, 27);
            panel.add(btn);
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    abrirVentana(req);
                }
            });
        }

        controlador = new ControladorPrincipal(this);
    }

    private void abrirVentana(int req) {
        switch (req) {
            case 1:  { VentanaAltaPostre v = new VentanaAltaPostre(); v.setVisible(true); break; }
            case 2:  { VentanaListadoPostres v = new VentanaListadoPostres(); v.setVisible(true); break; }
            case 3:  { VentanaDetallePostre v = new VentanaDetallePostre(); v.setVisible(true); break; }
            case 4:  { VentanaComenzarVenta v = new VentanaComenzarVenta(); v.setVisible(true); break; }
            case 5:  { VentanaAgregarPostreVenta v = new VentanaAgregarPostreVenta(); v.setVisible(true); break; }
            case 6:  { VentanaEliminarPostreDeVenta v = new VentanaEliminarPostreDeVenta(); v.setVisible(true); break; }
            case 7:  { VentanaFinalizarVenta v = new VentanaFinalizarVenta(); v.setVisible(true); break; }
            case 8:  { VentanaListadoVentas v = new VentanaListadoVentas(); v.setVisible(true); break; }
            case 9:  { VentanaPostresDeVenta v = new VentanaPostresDeVenta(); v.setVisible(true); break; }
            case 10: { VentanaRecaudacion v = new VentanaRecaudacion(); v.setVisible(true); break; }
            case 11: controlador.respaldarDatos(); break;
            case 12: controlador.recuperarDatos(); break;
        }
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public VentanaPrincipal() {
        this.initialize();
        this.setVisible(false);
    }
}