package logica.vo;
import java.io.Serializable;

public class VOItemOrden implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int numVenta;
	private String codigo;
	private String nombre;
	private float precio;
	private String tipo;
	private int cantUnidades;

	public VOItemOrden (int numVenta, String codigo,String nombre, float precio, String tipo, int cantUnidades) {
		this.numVenta = numVenta;
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
		this.cantUnidades = cantUnidades;
	}

	public int getNumVenta() {
	      return numVenta;
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
	
	public int getCantUnidades() {
	      return cantUnidades;
	}

}
