package logica;

public class ItemOrden {

    private Postre postre;
    private int cantidad;

    public ItemOrden(Postre postre, int cantidad) {
        this.postre = postre;
        this.cantidad = cantidad;
    }

    public Postre getPostre() {
        return postre;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public float calcularSubtotal() {
        return postre.getPrecio() * cantidad;
    }
    
}
