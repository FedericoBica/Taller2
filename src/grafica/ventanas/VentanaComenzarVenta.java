package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import logica.IFachada;

public class VentanaComenzarVenta {

    private JFrame frame;
    private JTextField textFieldFecha;
    private JTextField textFieldDireccion;
    private IFachada fachada;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Comenzar Venta");
        frame.setResizable(false);
        frame.setBounds(100, 100, 360, 160);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lFecha = new JLabel("Fecha (AAAA-MM-DD):");
        lFecha.setBounds(10, 15, 140, 14);
        panel.add(lFecha);

        textFieldFecha = new JTextField(LocalDate.now().toString());
        textFieldFecha.setBounds(155, 12, 175, 20);
        panel.add(textFieldFecha);

        JLabel lDir = new JLabel("Direccion entrega:");
        lDir.setBounds(10, 45, 140, 14);
        panel.add(lDir);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(155, 42, 175, 20);
        panel.add(textFieldDireccion);

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(200, 200, 200));
        btnCancelar.setBounds(50, 80, 110, 25);
        panel.add(btnCancelar);

        JButton btnCrear = new JButton("CREAR");
        btnCrear.setFont(new Font("Arial", Font.BOLD, 12));
        btnCrear.setBackground(new Color(173, 216, 230));
        btnCrear.setBounds(190, 80, 110, 25);
        panel.add(btnCrear);

        final VentanaComenzarVenta ventana = this;
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { ventana.crear(); }
        });
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });
    }

    private void crear() {
        String fechaStr  = textFieldFecha.getText().trim();
        String direccion = textFieldDireccion.getText().trim();
        if (fechaStr.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Completa todos los campos.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Formato invalido. Usa AAAA-MM-DD.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            fachada.comenzarVenta(fecha, direccion);
            JOptionPane.showMessageDialog(frame, "Venta creada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaComenzarVenta(IFachada fachada) {
        this.fachada = fachada;
        this.initialize();
        this.setVisible(false);
    }
}