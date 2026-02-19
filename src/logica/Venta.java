package logica;

public class Venta {
	 private int num;
	 private Fecha fecha;
	 private String direccionEntrega;
	 private String estado;
	 private float montoTotal;
	 private Orden orden;
	 
	 public Venta() {}
	 
	 public int getNumero() {
		 return num;
	 }

	 public Fecha getFecha() {
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
