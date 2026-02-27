package logica;

import java.time.LocalDate;

public class Venta {
	 private int num;
	 private LocalDate fecha;
	 private String direccionEntrega;
	 private String estado;
	 private Orden orden;
	 
	 public Venta(int num, LocalDate fecha, String direccionEntrega, String estado, float montoTotal) {
	     this.num = num;
	     this.fecha = fecha;
	     this.direccionEntrega = direccionEntrega;
	     this.estado = estado; 
	     this.orden = new Orden(40);
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

	 public Orden getOrden() {
		 return orden;
	 }

}
