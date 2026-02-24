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

import logica.vo.VOPostre;


public class CapaLogica {
  private TreeMap<String, Postre> abbPostres;
  private ArrayList<Venta> listaVentas;

  public CapaLogica(){
    abbPostres= new TreeMap<>(); //diccionario ordenado por codigo
    listaVentas= new ArrayList<>(); //secuencia ventas
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
public void listadoGeneralPostres() {
    for (Postre p : abbPostres.values()) {   // values() = todos los Postre
        String tipo = p.isLight() ? "LIGHT" : "NORMAL";
        System.out.println("Codigo es: " + p.getCodigo()
                + " Nombre: " + p.getNombre()
                + " Precio: " + p.getPrecio()
                + " Tipo: " + tipo);
    }
}
//REQUERIMIENTO: 3
public void listadoDetalladoPostre(String codigo)
    throws CodigoInvalidoException, PostreNoExisteException {
// Verificar si está en el diccionario
        if (!abbPostres.containsKey(codigo)) {
            throw new PostreNoExisteException("El postre no está registrado con codigo: " + codigo);
        }
// Obtengo el postre
    Postre p = abbPostres.get(codigo);
// mostrar datos del postre
    System.out.println("=DETALLE POSTRE=");
    System.out.println("Codigo: " + p.getCodigo());
    System.out.println("Nombre: " + p.getNombre());
    System.out.println("Precio: " + p.getPrecio());
    System.out.println("Tipo: " + p.getTipoPostre());

  // Si tipo=light entonces obtener además datos del light
    if (p.getTipoPostre().equals("LIGHT")) {
        Light l = (Light) p;
        System.out.println("Endulzante: " + l.getTipoEndulzante());
        System.out.println("Descripcion: " + l.getDescripcion());
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
}


//REQUERI

MIENTO: 5
