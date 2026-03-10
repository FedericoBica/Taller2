package logica.vo;
import java.io.Serializable;

public class VOFinalizacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private int numVenta;
    private boolean confirmar;

    public VOFinalizacion(int numVenta, boolean confirmar){
        this.numVenta = numVenta;
        this.confirmar = confirmar;
    }

    public int getNumVenta(){
    	return numVenta;
    }
    
    public boolean isConfirmar() {
        return confirmar;
    }
}
