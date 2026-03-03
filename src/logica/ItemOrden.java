package logica;

import java.io.Serializable;

public class ItemOrden implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Postre postre;
    private int cantidad;

    public ItemOrden(int cantidad, Postre postre) {
        this.cantidad = cantidad;
        this.postre = postre;
    }

    public Postre getPostre() {
        return postre;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
    	this.cantidad = cantidad;
    }
    
    public float calcularSubtotal() {
        return postre.getPrecio() * cantidad;
    }
    
}
