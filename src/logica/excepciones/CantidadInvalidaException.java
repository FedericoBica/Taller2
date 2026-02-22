package logica.excepciones;

public class CantidadInvalidaException extends Exception{
	
	private String mensaje;
	
	public CantidadInvalidaException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}

