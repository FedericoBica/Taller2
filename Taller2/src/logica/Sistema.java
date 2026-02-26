package logica;

import java.io.Serializable;

public class Sistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private Postres postres;
    private Ventas ventas;

    public Sistema(Postres postres, Ventas ventas) {
        this.postres = postres;
        this.ventas = ventas;
    }

    public Postres getPostres() {
        return postres;
    }

    public Ventas getVentas() {
        return ventas;
    }
}