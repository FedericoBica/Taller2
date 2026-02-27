package logica.excepciones;

public class LimiteUnidadesEnVentaException extends Exception{

  private static final long serialVersionUID = 1L;
 
  private String mensaje;

    public LimiteUnidadesEnVentaException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String darMensaje() {
        return mensaje;
    }
}
