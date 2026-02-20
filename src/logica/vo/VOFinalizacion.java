package logica.vo;

public class VOFinalizacion{
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
