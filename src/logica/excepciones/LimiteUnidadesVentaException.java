package logica.excepciones;

public class LimiteUnidadesVentaException extends Exception{

  private static final long serialVersionUID = 1L;
 
  private String mensaje;

    public LimiteUnidadesVentaException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String darMensaje() {
        return mensaje;
    }
}
