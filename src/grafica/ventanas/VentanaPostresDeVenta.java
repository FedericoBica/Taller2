package grafica.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import logica.vo.VOItemOrden;
import grafica.controladores.ControladorPostresDeVenta;

public class VentanaPostresDeVenta {

    private JFrame frame;
    private JTextField textFieldNumVenta;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ControladorPostresDeVenta controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Postres de una Venta");
        frame.setResizable(false);
        frame.setBounds(100, 100, 520, 380);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lNro = new JLabel("Nro. de Venta:"); lNro.setBounds(10,12,110,14); panel.add(lNro);
        textFieldNumVenta = new JTextField(); textFieldNumVenta.setBounds(125,9,120,20); panel.add(textFieldNumVenta);

        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(173,216,230));
        btnBuscar.setBounds(260,8,90,23); panel.add(btnBuscar);

        String[] columnas = {"Codigo", "Nombre", "Precio", "Tipo", "Cantidad"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10,40,485,280); panel.add(scroll);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(200,330,100,25); panel.add(btnCerrar);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = textFieldNumVenta.getText().trim();
                if (str.isEmpty()) { mostrarError("Ingresa el numero de venta."); return; }
                try {
                    controlador.buscarPostresDeVenta(Integer.parseInt(str));
                } catch (NumberFormatException ex) {
                    mostrarError("El numero debe ser un entero.");
                }
            }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorPostresDeVenta(this);
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarListado(ArrayList<VOItemOrden> lista) {
        modelo.setRowCount(0);
        for (VOItemOrden vo : lista) {
            modelo.addRow(new Object[]{
                vo.getCodigo(), vo.getNombre(),
                String.format("$ %.2f", vo.getPrecio()),
                vo.getTipo(), vo.getCantUnidades()
            });
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaPostresDeVenta() {
        this.initialize();
        this.setVisible(false);
    }
}