package logica.vo;

import java.io.Serializable;

public class VOPostreVenta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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

