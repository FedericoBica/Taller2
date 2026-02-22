package logica.vo;

public class VOItemOrden {
	private int numVenta;
	private String codigo;
	private float precio;
	private String tipo;
	private int cantUnidades;

	public VOItemOrden (int numVenta, String codigo, float precio, String tipo, int cantUnidades) {
		this.numVenta = numVenta;
		this.codigo = codigo;
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
