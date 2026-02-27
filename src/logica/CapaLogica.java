package logica;

import java.util.ArrayList;
import java.util.TreeMap;

import logica.excepciones.CantidadInvalidaException;
import logica.excepciones.CodigoInvalidoException;
import logica.excepciones.FechaInvalidaException;
import logica.excepciones.LimiteUnidadesEnVentaException;
import logica.excepciones.PostreNoEnVentaException;
import logica.excepciones.PostreNoExisteException;
import logica.excepciones.PostreYaExisteException;
import logica.excepciones.PrecioInvalidoException;
import logica.excepciones.VentaNoExisteException;
import logica.excepciones.VentaYaFinalizadaException;
import java.time.LocalDate;
import logica.Persistencia;
import logica.vo.VOPostreListado;
import logica.vo.VOPostreAlta;
import logica.vo.VORecaudacion;
import logica.vo.VOItemOrden;
import java.io.Serializable;



public class CapaLogica implements Serializable{
  private TreeMap<String, Postre> abbPostres;
  private ArrayList<Venta> listaVentas;
  //private ArrayList<Orden> orden;

  private Persistencia persistencia;

  public CapaLogica(){
    abbPostres= new TreeMap<>(); //diccionario ordenado por codigo
    listaVentas= new ArrayList<>(); //secuencia ventas
    //orden = new ArrayList<>(); //secuencia orden
  }

  //REQUERIMIENTO: 1
public void altaPostre(VOPostre vo)
  throws PostreYaExisteException, CodigoInvalidoException, PrecioInvalidoException {
        //Tomo los datos del VO
        String codigo = vo.getCodigo();
        String nombre = vo.getNombre();
        double precio = vo.getPrecio();
        boolean esLight = vo.isLight();
      //Validar codigo sea alfanumerico 
         if (!codigo.matches("[a-zA-Z0-9]+")) {
        throw new CodigoInvalidoException("Código inválido: debe ser alfanumérico.");
        }
        //Validar precio > 0
        if (precio <= 0) {
        throw new PrecioInvalidoException("El precio debe ser mayor a 0."); 
        }
       //Verifica si ya existe el postre en el diccionario  
      if (abbPostres.containsKey(codigo)) {
          throw new PostreYaExisteException("El postre ya está ingresado con código: " + codigo);
      }
      // Crear el Postre e insertarlo (si es light, creo Light con datos extra)
      Postre nuevo;
      if (esLight) {
        nuevo = new Light(codigo, nombre, precio, vo.getTipoEndulzante(), vo.getDescripcion());
    } else {
        nuevo = new Postre(codigo, nombre, precio);
    }

    abbPostres.put(codigo, nuevo);
}
  
//REQUERIMIENTO: 2
public ArrayList<VOPostreListado> listadoGeneralPostres() {

    ArrayList<VOPostreListado> lista = new ArrayList<>();
    
    for (Postre p : abbPostres.values()) {
    	 String tipo = (p instanceof Light) ? "LIGHT" : "NORMAL";
        VOPostreListado vo = new VOPostreListado(
                p.getCodigo(),
                p.getNombre(),
                p.getPrecio(),
                tipo
        );
        lista.add(vo);
    }
    return lista;
}


//REQUERIMIENTO: 3
public VOPostreAlta listadoDetalladoPostre(String codigo)
        throws PostreNoExisteException {
    if (!abbPostres.containsKey(codigo)) {
        throw new PostreNoExisteException("El postre no está registrado");
    }
    Postre p = abbPostres.get(codigo);
    if (p instanceof Light) {
        Light l = (Light) p;
        return new VOPostreAlta(
                l.getCodigo(),
                l.getNombre(),
                l.getPrecio(),
                "LIGHT",
                l.getTipoEndulzante(),
                l.getDescripcion()
        );
    } else {
        return new VOPostreAlta(
                p.getCodigo(),
                p.getNombre(),
                p.getPrecio(),
                "NORMAL",
                null,
                null
        );
    }
}

//REQUERIMIENTO: 4
public void comenzarVenta(LocalDate fecha, String direccion) throws FechaInvalidaException {

    // Si fecha ingresada es menor que la última fecha en ventas error
    if (!listaVentas.isEmpty()) {
        Venta ultima = listaVentas.get(listaVentas.size() - 1);
        if (fecha.isBefore(ultima.getFecha())) {
            throw new FechaInvalidaException("La fecha ingresada es menor que la última fecha registrada.");
        }
    }

    // sino: Insertar en ventas una venta con los datos de entrada
    Venta nueva = new Venta(fecha, direccion);
    listaVentas.add(nueva);
}


public VORecaudacion totalVentasPostreEnFecha(String codigoPostre, LocalDate fecha)
        throws PostreNoExisteException {

    if (!abbPostres.containsKey(codigoPostre)) {
        throw new PostreNoExisteException("El postre no está registrado");
    }

    float montoTotal = 0;
    int cantidadTotal = 0;

    for (Venta v : listaVentas) {

        if (v.getEstado().equals("FINALIZADA") &&
            v.getFecha().equals(fecha)) {

        	 Orden orden = v.getOrden();

             ItemOrden[] items = orden.getItems();
             int tope = orden.getTope();

             for (int i = 0; i < tope; i++) {

                 ItemOrden item = items[i];

                 if (item.getPostre().getCodigo().equals(codigoPostre)) {

                     montoTotal += item.getCantidad() * item.getPostre().getPrecio();
                     cantidadTotal += item.getCantidad();
                 }
            }
        }
    }

    return new VORecaudacion(montoTotal, cantidadTotal);
}

public void cargarDatos() {
	
    try {
    	 CapaLogica aux = persistencia.recuperar("datos.dat");

         this.abbPostres = aux.abbPostres;
         this.listaVentas = aux.listaVentas;

         System.out.println("Datos cargados correctamente");
    } catch (Exception e) {
        System.out.println("No había datos previos");
    }
}

public void guardarDatos() {

    try {
        persistencia.respaldar("datos.dat", this);
        System.out.println("Datos guardados correctamente");

    } catch (Exception e) {
        System.out.println("Error al guardar datos");
    }
}

}

