package grafica.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import logica.vo.VOPostreListado;
import grafica.controladores.ControladorListadoPostres;

public class VentanaListadoPostres {

    private JFrame frame;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ControladorListadoPostres controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Listado General de Postres");
        frame.setResizable(false);
        frame.setBounds(100, 100, 500, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Listado de Postres");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBounds(160, 10, 200, 20);
        panel.add(lblTitulo);

        String[] columnas = {"Codigo", "Nombre", "Precio", "Tipo"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 40, 465, 255);
        panel.add(scroll);

        JButton btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setBackground(new Color(173, 216, 230));
        btnActualizar.setBounds(100, 305, 120, 25);
        panel.add(btnActualizar);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200, 200, 200));
        btnCerrar.setBounds(270, 305, 120, 25);
        panel.add(btnCerrar);

        final VentanaListadoPostres ventana = this;
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.cargarListado(); }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorListadoPostres(this);
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarListado(ArrayList<VOPostreListado> lista) {
        modelo.setRowCount(0);
        for (VOPostreListado vo : lista) {
            modelo.addRow(new Object[]{
                vo.getCodigo(), vo.getNombre(),
                String.format("$ %.2f", vo.getPrecio()), vo.getTipo()
            });
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) {
        if (b) controlador.cargarListado();
        frame.setVisible(b);
    }

    public VentanaListadoPostres() {
        this.initialize();
        this.setVisible(false);
    }
}