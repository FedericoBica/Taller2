package logica;

import java.time.LocalDate;

public class Venta {
	 private int num;
	 private LocalDate fecha;
	 private String direccionEntrega;
	 private String estado;
	 private float montoTotal;
	 private Orden orden;
	 
	 public Venta(int num, LocalDate fecha, String direccionEntrega, String estado, float montoTotal) {
	     this.num = num;
	     this.fecha = fecha;
	     this.direccionEntrega = direccionEntrega;
	     this.estado = estado; 
	     this.montoTotal = montoTotal;
	     this.orden = new Orden();
	    }
	 
	 public int getNumero() {
		 return num;
	 }
	 
	 public LocalDate getFecha() {
	     return fecha;
	 }

	 public String getDireccionEntrega() {
		 return direccionEntrega;
	 }

	 public String getEstado() {
		 return estado;
	 }

	 public float getMontoTotal() {
		 return montoTotal;
	 }

	 public Orden getOrden() {
		 return orden;
	 }

}
