package logica.excepciones;

public class VentaYaFinalizadaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public VentaYaFinalizadaException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}
