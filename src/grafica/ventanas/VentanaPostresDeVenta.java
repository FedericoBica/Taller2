package grafica.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import logica.IFachada;
import logica.vo.VOPostreVenta;

public class VentanaPostresDeVenta {

    private JFrame frame;
    private JLabel labelNumVenta;
    private JTextField textFieldNumVenta;
    private JButton btnBuscar;
    private JButton btnCerrar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private IFachada fachada;

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

        labelNumVenta = new JLabel("Nro. de Venta:");
        labelNumVenta.setBounds(10, 12, 110, 14);
        panel.add(labelNumVenta);

        textFieldNumVenta = new JTextField();
        textFieldNumVenta.setBounds(125, 9, 120, 20);
        panel.add(textFieldNumVenta);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(173, 216, 230));
        btnBuscar.setBounds(260, 8, 90, 23);
        panel.add(btnBuscar);

        String[] columnas = {"Codigo", "Nombre", "Precio", "Tipo", "Cantidad"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 40, 485, 280);
        panel.add(scroll);

        btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200, 200, 200));
        btnCerrar.setBounds(200, 330, 100, 25);
        panel.add(btnCerrar);

        final VentanaPostresDeVenta ventana = this;

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.buscar();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

  /*  private void buscar() {
        
    }*/

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public VentanaPostresDeVenta(IFachada fachada) {
        this.fachada = fachada;
        this.initialize();
        this.setVisible(false);
    }
}
