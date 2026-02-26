package logica.excepciones;

public class PostreNoExisteException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public PostreNoExisteException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}
