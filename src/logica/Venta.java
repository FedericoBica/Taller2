package logica;

import java.io.Serializable;
import java.time.LocalDate;

public class Venta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static int contadorNum = 1;
	
	private int num;
	private LocalDate fecha;
	private String direccionEntrega;
	private String estado;
	private Orden orden;
	 
	public Venta( LocalDate fecha, String direccionEntrega, String estado, float montoTotal) {
	     this.num = contadorNum++;
	     this.fecha = fecha;
	     this.direccionEntrega = direccionEntrega;
	     this.estado = "EN_PROCESO"; 
	     this.orden = new Orden(40);
	 }
	
	public Venta(LocalDate fecha, String direccionEntrega) {
	    this.num = contadorNum++;
	    this.fecha = fecha;
	    this.direccionEntrega = direccionEntrega;
	    this.estado = "EN_PROCESO";
	    this.orden = new Orden(40);
	}
	 
	 public int getNumero() {
		 return num;
	 }
	 
	 public LocalDate getFecha() {
	     return fecha;
	 }
	 
	public static void setContadorNumero(int nuevo) {
		contadorNum = nuevo;
	}

	 public String getDireccionEntrega() {
		 return direccionEntrega;
	 }

	 public String getEstado() {
		 return estado;
	 }

	 public Orden getOrden() {
		 return orden;
	 }
	 
	 public void setEstado(String estado) {
		this.estado = estado;    
	}
	 
	public float getMontoTotal() {
	    return orden.calcularMontoTotal();
	}

}
