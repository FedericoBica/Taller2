package logica.vo;

import java.io.Serializable;
import java.time.LocalDate;

public class VOVentaAlta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    public LocalDate fechaVenta;
    public String direccionEntrega;

    public VOVentaAlta(LocalDate fechaVenta, String direccionEntrega){
        this.fechaVenta=fechaVenta;
        this.direccionEntrega=direccionEntrega; 
    }

    public LocalDate getFechaVenta(){
    	return fechaVenta;
    }
    public String getDireccionEntrega(){
    	return direccionEntrega;
    }
}

