package logica.excepciones;

public class VentaNoExisteException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public VentaNoExisteException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}
