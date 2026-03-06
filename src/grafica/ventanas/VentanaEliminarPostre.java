package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logica.IFachada;
import logica.vo.VOPostreVenta;

public class VentanaEliminarPostre {

    private JFrame frame;
    private JTextField textFieldNumVenta;
    private JTextField textFieldCodigo;
    private JTextField textFieldCantidad;
    private IFachada fachada;

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
        btnCerrar.setBackground(new Color(200, 200, 200));
        btnCerrar.setBounds(50, 110, 100, 25);
        panel.add(btnCerrar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setBackground(new Color(255, 182, 193));
        btnEliminar.setBounds(180, 110, 100, 25);
        panel.add(btnEliminar);

        final VentanaEliminarPostre ventana = this;
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { ventana.eliminar(); }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });
    }

    private void eliminar() {
        String codigo = textFieldCodigo.getText().trim();
        String cantStr = textFieldCantidad.getText().trim();
        String numStr = textFieldNumVenta.getText().trim();
        if (codigo.isEmpty() || cantStr.isEmpty() || numStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Completa todos los campos.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int cantidad, numVenta;
        try { 
        	cantidad = Integer.parseInt(cantStr); 
        	numVenta = Integer.parseInt(numStr); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cantidad y Nro deben ser enteros.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            fachada.eliminarPostreDeVenta(new VOPostreVenta(codigo, cantidad, numVenta));
            JOptionPane.showMessageDialog(frame, "Postre eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            textFieldCodigo.setText(""); textFieldCantidad.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean b) { 
    	frame.setVisible(b); 
    }

    public VentanaEliminarPostre(IFachada fachada) {
        this.fachada = fachada;
        this.initialize();
        this.setVisible(false);
    }
}
