package logica.excepciones;

public class CantidadInvalidaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public CantidadInvalidaException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}

