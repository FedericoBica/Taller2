package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import grafica.controladores.ControladorAgregarPostreVenta;

public class VentanaAgregarPostreVenta {

    private JFrame frame;
    private JTextField textFieldNumVenta;
    private JTextField textFieldCodigo;
    private JTextField textFieldCantidad;
    private ControladorAgregarPostreVenta controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Agregar Postre a Venta");
        frame.setResizable(false);
        frame.setBounds(100, 100, 340, 175);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lNro = new JLabel("Nro. de Venta:"); lNro.setBounds(10,15,110,14); panel.add(lNro);
        textFieldNumVenta = new JTextField(); textFieldNumVenta.setBounds(130,12,180,20); panel.add(textFieldNumVenta);

        JLabel lCod = new JLabel("Codigo Postre:"); lCod.setBounds(10,45,110,14); panel.add(lCod);
        textFieldCodigo = new JTextField(); textFieldCodigo.setBounds(130,42,180,20); panel.add(textFieldCodigo);

        JLabel lCant = new JLabel("Cantidad:"); lCant.setBounds(10,75,110,14); panel.add(lCant);
        textFieldCantidad = new JTextField(); textFieldCantidad.setBounds(130,72,180,20); panel.add(textFieldCantidad);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(50,110,100,25); panel.add(btnCerrar);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setBackground(new Color(173,216,230));
        btnAgregar.setBounds(180,110,100,25); panel.add(btnAgregar);

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { agregar(); }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorAgregarPostreVenta(this);
    }

    private void agregar() {
        String codigo = textFieldCodigo.getText().trim();
        String cantStr = textFieldCantidad.getText().trim();
        String nroStr = textFieldNumVenta.getText().trim();
        if (codigo.isEmpty() || cantStr.isEmpty() || nroStr.isEmpty()) {
            mostrarError("Completa todos los campos.");
            return;
        }
        try {
            int cantidad = Integer.parseInt(cantStr);
            int numVenta = Integer.parseInt(nroStr);
            controlador.agregarPostre(codigo, cantidad, numVenta);
        } catch (NumberFormatException ex) {
            mostrarError("Cantidad y Nro. deben ser enteros.");
        }
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCampos() {
        textFieldCodigo.setText("");
        textFieldCantidad.setText("");
    }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaAgregarPostreVenta() {
        this.initialize();
        this.setVisible(false);
    }
}