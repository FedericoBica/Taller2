package logica;

public class Postre {
	private String codigo;
	private String nombre;
	private float precio;
	
	public Postre(String codigo, String nombre, float precio) {
		this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }
	
	public String getCodigo() {
		return codigo;
	}
	
    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }
}
