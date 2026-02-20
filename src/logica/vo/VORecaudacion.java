package logica.vo;

public class VORecaudacion {
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

