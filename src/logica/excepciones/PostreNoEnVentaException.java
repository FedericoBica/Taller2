package logica.excepciones;

public class PostreNoEnVentaException extends Exception{

  private String mensaje;

public PostreNoEnVentaException(String mensaje){
    this.mensaje=mensaje;
}

public String darMensaje(){

}
} 
