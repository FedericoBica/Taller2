package logica;

import java.io.Serializable;

public class Postre implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nombre;
	private float precio;
	
	public Postre(String codigo, String nombre, float precio) {
		this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }
	
	public String getCodigo() {
		return codigo;
	}
	
    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }
    
    public String getTipoPostre() {
    	return "NORMAL"; 
    }
}
