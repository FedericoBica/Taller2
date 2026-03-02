package logica.vo;
import java.io.Serializable;

public class VOFinalizacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private int numVenta;
    private String indicacion;

    public VOFinalizacion(int numVenta, String indicacion){
        this.numVenta = numVenta;
        this.indicacion = indicacion;
    }

    public int getNumVenta(){
    	return numVenta;
    }
    
    public String getIndicacion(){
    	return indicacion;
    }
}
