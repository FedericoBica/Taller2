package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import grafica.controladores.ControladorAltaPostre;

public class VentanaAltaPostre {

    private JFrame frame;
    private JTextField textFieldCodigo;
    private JTextField textFieldNombre;
    private JTextField textFieldPrecio;
    private JTextField textFieldEndulzante;
    private JTextField textFieldDescripcion;
    private JCheckBox chkboxLight;
    private ControladorAltaPostre controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Alta de Postre");
        frame.setResizable(false);
        frame.setBounds(100, 100, 380, 280);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel labelCodigo = new JLabel("Codigo:"); labelCodigo.setBounds(10,15,90,14); panel.add(labelCodigo);
        textFieldCodigo = new JTextField(); textFieldCodigo.setBounds(110,12,240,20); panel.add(textFieldCodigo);

        JLabel labelNombre = new JLabel("Nombre:"); labelNombre.setBounds(10,45,90,14); panel.add(labelNombre);
        textFieldNombre = new JTextField(); textFieldNombre.setBounds(110,42,240,20); panel.add(textFieldNombre);

        JLabel labelPrecio = new JLabel("Precio:"); labelPrecio.setBounds(10,75,90,14); panel.add(labelPrecio);
        textFieldPrecio = new JTextField(); textFieldPrecio.setBounds(110,72,240,20); panel.add(textFieldPrecio);

        JLabel labelLight = new JLabel("Tipo:"); labelLight.setBounds(10,105,90,14); panel.add(labelLight);
        chkboxLight = new JCheckBox("Es Light?");
        chkboxLight.setBackground(new Color(240,248,255));
        chkboxLight.setBounds(108,101,100,23);
        panel.add(chkboxLight);

        JLabel labelEndulzante = new JLabel("Endulzante:"); labelEndulzante.setBounds(10,135,90,14); panel.add(labelEndulzante);
        textFieldEndulzante = new JTextField(); textFieldEndulzante.setBounds(110,132,240,20); textFieldEndulzante.setEnabled(false); panel.add(textFieldEndulzante);

        JLabel labelDescripcion = new JLabel("Descripcion:"); labelDescripcion.setBounds(10,165,90,14); panel.add(labelDescripcion);
        textFieldDescripcion = new JTextField(); textFieldDescripcion.setBounds(110,162,240,20); textFieldDescripcion.setEnabled(false); panel.add(textFieldDescripcion);

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(200,200,200));
        btnCancelar.setBounds(60,200,110,25);
        panel.add(btnCancelar);

        JButton btnGuardar = new JButton("GUARDAR");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(173,216,230));
        btnGuardar.setBounds(190,200,110,25);
        panel.add(btnGuardar);

        chkboxLight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean esLight = chkboxLight.isSelected();
                textFieldEndulzante.setEnabled(esLight);
                textFieldDescripcion.setEnabled(esLight);
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorAltaPostre(this);
    }

    private void guardar() {
        String codigo    = textFieldCodigo.getText().trim();
        String nombre    = textFieldNombre.getText().trim();
        String precioStr = textFieldPrecio.getText().trim();
        boolean esLight  = chkboxLight.isSelected();

        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
            mostrarError("Completa todos los campos obligatorios.");
            return;
        }
        float precio;
        try {
            precio = Float.parseFloat(precioStr);
        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un numero.");
            return;
        }
        String endulzante  = textFieldEndulzante.getText().trim();
        String descripcion = textFieldDescripcion.getText().trim();
        controlador.altaPostre(codigo, nombre, precio, esLight, endulzante, descripcion);
    }

    //invocados por el controlador
    
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCampos() {
        textFieldCodigo.setText("");
        textFieldNombre.setText("");
        textFieldPrecio.setText("");
        textFieldEndulzante.setText("");
        textFieldDescripcion.setText("");
        chkboxLight.setSelected(false);
        textFieldEndulzante.setEnabled(false); 
        textFieldDescripcion.setEnabled(false);
        frame.dispose();
    }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaAltaPostre() {
        this.initialize();
        this.setVisible(false);
    }
}