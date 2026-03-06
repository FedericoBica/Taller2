package grafica.ventanas;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logica.vo.VOPostreAlta;
import grafica.controladores.ControladorDetallePostre;

public class VentanaDetallePostre {

    private JFrame frame;
    private JTextField textFieldCodigo;
    private JLabel lblValNombre;
    private JLabel lblValPrecio;
    private JLabel lblValTipo;
    private JLabel lblValEndulzante;
    private JLabel lblValDescripcion;
    private ControladorDetallePostre controlador;

    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setBackground(new Color(240, 248, 255));
        frame.setTitle("Detalle de Postre");
        frame.setResizable(false);
        frame.setBounds(100, 100, 380, 290);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel lCodigo = new JLabel("Codigo:"); lCodigo.setBounds(10,15,100,14); panel.add(lCodigo);
        textFieldCodigo = new JTextField(); textFieldCodigo.setBounds(120,12,150,20); panel.add(textFieldCodigo);

        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(173,216,230));
        btnBuscar.setBounds(280,10,85,24); panel.add(btnBuscar);

        JLabel sep = new JLabel("________________________________________________");
        sep.setBounds(5,40,360,14); panel.add(sep);

        panel.add(makeLabel("Nombre:",     10, 60));  lblValNombre     = makeValor(120, 60);  panel.add(lblValNombre);
        panel.add(makeLabel("Precio:",     10, 85));  lblValPrecio     = makeValor(120, 85);  panel.add(lblValPrecio);
        panel.add(makeLabel("Tipo:",       10,110));  lblValTipo       = makeValor(120,110);  panel.add(lblValTipo);
        panel.add(makeLabel("Endulzante:", 10,135));  lblValEndulzante = makeValor(120,135);  panel.add(lblValEndulzante);
        panel.add(makeLabel("Descripcion:",10,160));  lblValDescripcion= makeValor(120,160);  panel.add(lblValDescripcion);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(200,200,200));
        btnCerrar.setBounds(130,210,110,25); panel.add(btnCerrar);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = textFieldCodigo.getText().trim();
                if (codigo.isEmpty()) { mostrarError("Ingresa un codigo."); return; }
                controlador.buscarPostre(codigo);
            }
        });
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });

        controlador = new ControladorDetallePostre(this);
    }

    private JLabel makeLabel(String t, int x, int y) { JLabel l = new JLabel(t); l.setBounds(x,y,100,14); return l; }
    private JLabel makeValor(int x, int y) { JLabel l = new JLabel("-"); l.setFont(new Font("Arial",Font.BOLD,12)); l.setBounds(x,y,240,14); return l; }

    // ── Métodos invocados por el controlador ──────────────────────────────
    public void mostrarDetalle(VOPostreAlta vo) {
        lblValNombre.setText(vo.getNombre());
        lblValPrecio.setText(String.format("$ %.2f", vo.getPrecio()));
        lblValTipo.setText(vo.getTipo());
        lblValEndulzante.setText(vo.getTipoEndulzante() != null ? vo.getTipoEndulzante() : "-");
        lblValDescripcion.setText(vo.getDescripcion() != null ? vo.getDescripcion() : "-");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) { frame.setVisible(b); }

    public VentanaDetallePostre() {
        this.initialize();
        this.setVisible(false);
    }
}