package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logica.IFachada;
import logica.vo.VOPostreAlta;

public class VentanaAltaPostre {

    private JFrame frame;
    private JLabel labelCodigo;
    private JLabel labelNombre;
    private JLabel labelPrecio;
    private JLabel labelLight;
    private JLabel labelEndulzante;
    private JLabel labelDescripcion;
    private JTextField textFieldCodigo;
    private JTextField textFieldNombre;
    private JTextField textFieldPrecio;
    private JTextField textFieldEndulzante;
    private JTextField textFieldDescripcion;
    private JCheckBox chkboxLight;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private IFachada fachada;

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

        labelCodigo = new JLabel("Codigo:");
        labelCodigo.setBounds(10, 15, 90, 14);
        panel.add(labelCodigo);

        textFieldCodigo = new JTextField();
        textFieldCodigo.setBounds(110, 12, 240, 20);
        panel.add(textFieldCodigo);

        labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(10, 45, 90, 14);
        panel.add(labelNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(110, 42, 240, 20);
        panel.add(textFieldNombre);

        labelPrecio = new JLabel("Precio:");
        labelPrecio.setBounds(10, 75, 90, 14);
        panel.add(labelPrecio);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(110, 72, 240, 20);
        panel.add(textFieldPrecio);

        labelLight = new JLabel("Tipo:");
        labelLight.setBounds(10, 105, 90, 14);
        panel.add(labelLight);

        chkboxLight = new JCheckBox("Es Light?");
        chkboxLight.setBackground(new Color(240, 248, 255));
        chkboxLight.setBounds(108, 101, 100, 23);
        panel.add(chkboxLight);

        labelEndulzante = new JLabel("Endulzante:");
        labelEndulzante.setBounds(10, 135, 90, 14);
        panel.add(labelEndulzante);

        textFieldEndulzante = new JTextField();
        textFieldEndulzante.setBounds(110, 132, 240, 20);
        textFieldEndulzante.setEnabled(false);
        panel.add(textFieldEndulzante);

        labelDescripcion = new JLabel("Descripcion:");
        labelDescripcion.setBounds(10, 165, 90, 14);
        panel.add(labelDescripcion);

        textFieldDescripcion = new JTextField();
        textFieldDescripcion.setBounds(110, 162, 240, 20);
        textFieldDescripcion.setEnabled(false);
        panel.add(textFieldDescripcion);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(200, 200, 200));
        btnCancelar.setBounds(60, 200, 110, 25);
        panel.add(btnCancelar);

        btnGuardar = new JButton("GUARDAR");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(173, 216, 230));
        btnGuardar.setBounds(190, 200, 110, 25);
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
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private void guardar() {
        String codigo    = textFieldCodigo.getText().trim();
        String nombre    = textFieldNombre.getText().trim();
        String precioStr = textFieldPrecio.getText().trim();
        boolean esLight  = chkboxLight.isSelected();

        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Completa todos los campos obligatorios.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        float precio;
        try {
            precio = Float.parseFloat(precioStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "El precio debe ser un numero.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            VOPostreAlta vo;
            if (esLight) {
                String endulzante  = textFieldEndulzante.getText().trim();
                String descripcion = textFieldDescripcion.getText().trim();
                vo = new VOPostreAlta(codigo, nombre, precio, "LIGHT", endulzante, descripcion);
            } else {
                vo = new VOPostreAlta(codigo, nombre, precio, "NORMAL");
            }
            fachada.altaPostre(vo);
            JOptionPane.showMessageDialog(frame, "Postre dado de alta correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public VentanaAltaPostre(IFachada fachada) {
        this.fachada = fachada;
        this.initialize();
        this.setVisible(false);
    }
}
