package vo;

public class VOVenta {
	 private int num;
	 private String fecha;
	 private String direccionEntrega;
	 private String estado;
	 private float montoTotal;	

	 public VOVenta() {}
	 
	 public int getNum() {
		 return num;
	 }
	 
	 public String getFecha() {
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
}
