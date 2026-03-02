package logica;

public class Light extends Postre {
	
	private static final long serialVersionUID = 1L;
	
	private String tipoEndulzante;
	private String descripcion;

    public Light(String codigo, String nombre, float precio, String tipoEndulzante, String descripcion) {
        super(codigo, nombre, precio);
        this.tipoEndulzante = tipoEndulzante;
        this.descripcion = descripcion;
    }

    public String getTipoEndulzante() {
        return tipoEndulzante;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getTipoPostre() {
    	return "LIGHT"; 
    }
}