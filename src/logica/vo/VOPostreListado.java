package logica.vo;

import java.io.Serializable;

public class VOPostreListado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String codigo;
    private String nombre;
    private float precio;
    private String tipo;
    
    public VOPostreListado(String codigo, String nombre, float precio, String tipo) {
       this.codigo = codigo;
       this.nombre = nombre;
       this.precio = precio;
       this.tipo = tipo;
    }

	public String getCodigo(){
	  return codigo;
	}
	
	public String getNombre(){
	  return nombre;
	}
	
	public float getPrecio(){
	  return precio;
	}
	
	public String getTipo(){
	  return tipo;
	}
}