package grafica.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import logica.vo.VOVenta;
import grafica.controladores.ControladorListadoVentas;

public class VentanaListadoVentas {

    private JFrame frame;
    private JRadioButton rbTodas;
    private JRadioButton rbProceso;
    private JRadioButton rbFinalizadas;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ControladorListadoVentas controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Listado de Ventas");
        frame.setResizable(false);
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lblFiltro = new JLabel("Mostrar:"); lblFiltro.setBounds(10,12,65,14); panel.add(lblFiltro);

        rbTodas = new JRadioButton("Todas"); rbTodas.setBackground(new Color(240,248,255)); rbTodas.setSelected(true); rbTodas.setBounds(80,8,80,23); panel.add(rbTodas);
        rbProceso = new JRadioButton("En Proceso"); rbProceso.setBackground(new Color(240,248,255)); rbProceso.setBounds(165,8,100,23); panel.add(rbProceso);
        rbFinalizadas = new JRadioButton("Finalizadas"); rbFinalizadas.setBackground(new Color(240,248,255)); rbFinalizadas.setBounds(270,8,105,23); panel.add(rbFinalizadas);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbTodas); grupo.add(rbProceso); grupo.add(rbFinalizadas);

        JButton btnFiltrar = new JButton("FILTRAR");
        btnFiltrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnFiltrar.setBackground(new Color(173,216,230));
        btnFiltrar.setBounds(385,8,90,23); panel.add(btnFiltrar);

        String[] columnas = {"Nro.", "Fecha", "Direccion", "Estado", "Monto"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10,40,565,295); panel.add(scroll);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(240,345,100,25); panel.add(btnCerrar);

        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char tipo = rbTodas.isSelected() ? 'T' : rbProceso.isSelected() ? 'P' : 'F';
                controlador.cargarVentas(tipo);
            }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorListadoVentas(this);
    }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarListado(ArrayList<VOVenta> lista) {
        modelo.setRowCount(0);
        for (VOVenta v : lista) {
            modelo.addRow(new Object[]{
                v.getNum(), v.getFecha(), v.getDireccionEntrega(),
                v.getEstado(), String.format("$ %.2f", v.getMontoTotal())
            });
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) {
        if (b) controlador.cargarVentas('T');
        frame.setVisible(b);
    }

    public VentanaListadoVentas() {
        this.initialize();
        this.setVisible(false);
    }
}