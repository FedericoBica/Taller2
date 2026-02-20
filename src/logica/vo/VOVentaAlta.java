package logica.vo;
import logica.Fecha;

public class VOVentaAlta {
    public Fecha fechaVenta;
    public String direccionEntrega;

    public VOVentaAlta(Fecha fechaVenta, String direccionEntrega){
        this.fechaVenta=fechaVenta;
        this.direccionEntrega=direccionEntrega; 
    }

    public Fecha getFechaVenta(){
    	return fechaVenta;
    }
    public String getDireccionEntrega(){
    	return direccionEntrega;
    }
}

