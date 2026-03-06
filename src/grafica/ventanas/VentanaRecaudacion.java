package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import logica.vo.VORecaudacion;
import grafica.controladores.ControladorRecaudacion;

public class VentanaRecaudacion {

    private JFrame frame;
    private JTextField textFieldCodigo;
    private JTextField textFieldFecha;
    private JLabel lblValCantidad;
    private JLabel lblValMonto;
    private ControladorRecaudacion controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Recaudacion por Postre y Fecha");
        frame.setResizable(false);
        frame.setBounds(100, 100, 380, 240);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lCod = new JLabel("Codigo Postre:"); lCod.setBounds(10,15,120,14); panel.add(lCod);
        textFieldCodigo = new JTextField(); textFieldCodigo.setBounds(140,12,210,20); panel.add(textFieldCodigo);

        JLabel lFecha = new JLabel("Fecha (AAAA-MM-DD):"); lFecha.setBounds(10,45,140,14); panel.add(lFecha);
        textFieldFecha = new JTextField(LocalDate.now().toString()); textFieldFecha.setBounds(155,42,195,20); panel.add(textFieldFecha);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(50,75,110,25); panel.add(btnCerrar);

        JButton btnConsultar = new JButton("CONSULTAR");
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 12));
        btnConsultar.setBackground(new Color(173,216,230));
        btnConsultar.setBounds(200,75,110,25); panel.add(btnConsultar);

        JLabel sep = new JLabel("________________________________________________");
        sep.setBounds(5,108,360,14); panel.add(sep);

        JLabel lCant = new JLabel("Unidades vendidas:"); lCant.setBounds(10,130,130,14); panel.add(lCant);
        lblValCantidad = new JLabel("-"); lblValCantidad.setFont(new Font("Arial",Font.BOLD,13)); lblValCantidad.setBounds(145,130,200,14); panel.add(lblValCantidad);

        JLabel lMonto = new JLabel("Monto total:"); lMonto.setBounds(10,160,130,14); panel.add(lMonto);
        lblValMonto = new JLabel("-"); lblValMonto.setFont(new Font("Arial",Font.BOLD,13)); lblValMonto.setBounds(145,160,200,14); panel.add(lblValMonto);

        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { consultar(); }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorRecaudacion(this);
    }

    private void consultar() {
        String codigo = textFieldCodigo.getText().trim();
        String fechaStr = textFieldFecha.getText().trim();
        if (codigo.isEmpty() || fechaStr.isEmpty()) {
            mostrarError("Completa todos los campos.");
            return;
        }
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            mostrarError("Formato invalido. Usa AAAA-MM-DD.");
            return;
        }
        controlador.consultarRecaudacion(codigo, fecha);
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarRecaudacion(VORecaudacion rec) {
        lblValCantidad.setText(String.valueOf(rec.getCantUnidades()));
        lblValMonto.setText(String.format("$ %.2f", rec.getMontoTotal()));
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaRecaudacion() {
        this.initialize();
        this.setVisible(false);
    }
}