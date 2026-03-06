package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import grafica.controladores.ControladorFinalizarVenta;

public class VentanaFinalizarVenta {

    private JFrame frame;
    private JTextField textFieldNumVenta;
    private JRadioButton rbConfirmar;
    private JRadioButton rbCancelar;
    private ControladorFinalizarVenta controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Finalizar Venta");
        frame.setResizable(false);
        frame.setBounds(100, 100, 340, 190);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lNro = new JLabel("Nro. de Venta:"); lNro.setBounds(10,15,110,14); panel.add(lNro);
        textFieldNumVenta = new JTextField(); textFieldNumVenta.setBounds(130,12,180,20); panel.add(textFieldNumVenta);

        JLabel lAccion = new JLabel("Accion:"); lAccion.setBounds(10,50,110,14); panel.add(lAccion);

        rbConfirmar = new JRadioButton("Confirmar (pagar)");
        rbConfirmar.setBackground(new Color(240,248,255));
        rbConfirmar.setSelected(true);
        rbConfirmar.setBounds(128,45,180,23); panel.add(rbConfirmar);

        rbCancelar = new JRadioButton("Cancelar (eliminar)");
        rbCancelar.setBackground(new Color(240,248,255));
        rbCancelar.setBounds(128,70,180,23); panel.add(rbCancelar);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbConfirmar); grupo.add(rbCancelar);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(50,120,100,25); panel.add(btnCerrar);

        JButton btnFinalizar = new JButton("FINALIZAR");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnFinalizar.setBackground(new Color(173,216,230));
        btnFinalizar.setBounds(180,120,100,25); panel.add(btnFinalizar);

        btnFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { finalizar(); }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorFinalizarVenta(this);
    }

    private void finalizar() {
        String nroStr = textFieldNumVenta.getText().trim();
        if (nroStr.isEmpty()) { mostrarError("Ingresa el numero de venta."); return; }
        try {
            int numVenta = Integer.parseInt(nroStr);
            controlador.finalizarVenta(numVenta, rbConfirmar.isSelected());
        } catch (NumberFormatException ex) {
            mostrarError("El numero debe ser un entero.");
        }
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void cerrar() { frame.dispose(); }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaFinalizarVenta() {
        this.initialize();
        this.setVisible(false);
    }
}