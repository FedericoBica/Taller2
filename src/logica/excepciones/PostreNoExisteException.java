package logica.excepciones;

public class PostreNoExisteException extends Exception {
	private String mensaje;
	
	public PostreNoExisteException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}
