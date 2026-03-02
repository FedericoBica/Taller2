package logica.vo;

import java.io.Serializable;

public class VOPostreAlta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nombre;
	private float precio;
	private String tipo;
	private String tipoEndulzante;
	private String descripcion;


	public VOPostreAlta(String codigo, String nombre, float precio, String tipo, String tipoEndulzante, String descripcion){
		this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.tipoEndulzante = tipoEndulzante;
        this.descripcion = descripcion;

	}
	
	public VOPostreAlta(String codigo, String nombre, float precio, String tipo) {
		this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
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

	public String getTipo() {
	        return tipo;
	}

	public String getTipoEndulzante() {
	        return tipoEndulzante;
	}

	public String getDescripcion() {
	        return descripcion;
	}
	
	 public boolean isLight() {
		 return "LIGHT".equals(tipo); 
	}
	
}
