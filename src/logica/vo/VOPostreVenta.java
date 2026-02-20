package logica.vo;

public class VOPostreVenta {
    private String codigo;
    private int cantidad;
    private int numVenta;

    public VOPostreVenta(String codigo, int cantidad, int numVenta){
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.numVenta = numVenta;
    }
    public String getCodigo(){
    	return codigo;
    }
    public int getCantidad(){
    	return cantidad;
    }
    public int getNumVenta(){
    	return numVenta;
    }
}

