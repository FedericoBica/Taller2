package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Venta> ventas;

    public Ventas() {
        ventas = new ArrayList<>();
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }
}
