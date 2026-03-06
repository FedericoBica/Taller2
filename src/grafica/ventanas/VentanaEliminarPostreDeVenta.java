package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import grafica.controladores.ControladorEliminarPostre;

public class VentanaEliminarPostreDeVenta {

    private JFrame frame;
    private JTextField textFieldNumVenta;
    private JTextField textFieldCodigo;
    private JTextField textFieldCantidad;
    private ControladorEliminarPostre controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Eliminar Postre de Venta");
        frame.setResizable(false);
        frame.setBounds(100, 100, 340, 175);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lNum = new JLabel("Nro. de Venta:"); lNum.setBounds(10,15,110,14); panel.add(lNum);
        textFieldNumVenta = new JTextField(); textFieldNumVenta.setBounds(130,12,180,20); panel.add(textFieldNumVenta);

        JLabel lCod = new JLabel("Codigo Postre:"); lCod.setBounds(10,45,110,14); panel.add(lCod);
        textFieldCodigo = new JTextField(); textFieldCodigo.setBounds(130,42,180,20); panel.add(textFieldCodigo);

        JLabel lCant = new JLabel("Cantidad:"); lCant.setBounds(10,75,110,14); panel.add(lCant);
        textFieldCantidad = new JTextField(); textFieldCantidad.setBounds(130,72,180,20); panel.add(textFieldCantidad);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(50,110,100,25); panel.add(btnCerrar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setBackground(new Color(255,182,193));
        btnEliminar.setBounds(180,110,100,25); panel.add(btnEliminar);

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { eliminar(); }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorEliminarPostre(this);
    }

    private void eliminar() {
        String codigo = textFieldCodigo.getText().trim();
        String cantStr = textFieldCantidad.getText().trim();
        String numStr = textFieldNumVenta.getText().trim();
        if (codigo.isEmpty() || cantStr.isEmpty() || numStr.isEmpty()) {
            mostrarError("Completa todos los campos.");
            return;
        }
        try {
            int cantidad = Integer.parseInt(cantStr);
            int numVenta = Integer.parseInt(numStr);
            controlador.eliminarPostre(codigo, cantidad, numVenta);
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

    public VentanaEliminarPostreDeVenta() {
        this.initialize();
        this.setVisible(false);
    }
}