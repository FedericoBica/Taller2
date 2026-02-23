package logica.excepciones;

public class FechaInvalidaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public FechaInvalidaException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}
