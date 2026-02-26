package logica.excepciones;

public class CodigoInvalidoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public CodigoInvalidoException(String mensaje) {
		this.mensaje = mensaje;
	}
	 
	public String darMensaje() {
		return mensaje;
	}
}
