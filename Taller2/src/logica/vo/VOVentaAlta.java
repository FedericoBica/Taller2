package logica.vo;

import java.time.LocalDate;

public class VOVentaAlta {
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

