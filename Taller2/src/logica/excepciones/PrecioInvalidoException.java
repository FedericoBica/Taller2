package logica.excepciones;

public class PrecioInvalidoException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public PrecioInvalidoException(String mensaje) {
		this.mensaje = mensaje;
	}
	public String darMensaje() {
		return mensaje;
	}
}
