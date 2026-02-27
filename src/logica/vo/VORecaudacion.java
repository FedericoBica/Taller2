package logica.vo;
import java.io.Serializable;

public class VORecaudacion implements Serializable {
    public float montoTotal;
    public int cantUnidades;

    public VORecaudacion(float montoTotal, int cantUnidades){
        this.montoTotal = montoTotal;
        this.cantUnidades = cantUnidades;
    }

    public float getMontoTotal(){
    	return montoTotal;
    }
    public int getCantUnidades(){
    	return cantUnidades;
    }
}

