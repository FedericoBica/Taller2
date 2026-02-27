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


/*public VORecaudacion totalVentasPostreEnFecha(String codigoPostre, LocalDate fecha)
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
*/

//REQUERIMIENTO: 5
public void agregarPostreAVenta(String codigo, int cantidad, int numeroVenta)
        throws VentaNoExisteException, VentaYaFinalizadaException,
               PostreNoExisteException, CantidadInvalidaException {
 Venta ventaEncontrada = null;

  int i=0;
while (i < listaVentas.size() && ventaEncontrada == null) {
      Venta v = listaVentas.get(i);
       if (v.getNumero() == numeroVenta) {
        ventaEncontrada = v;
    }

    i++;
}
if (ventaEncontrada == null) {
        throw new VentaNoExisteException("No existe la venta con el numero: " + numeroVenta);
  }
//Accedo y verifico si esta en proceso
    if (!ventaEncontrada.getEstado().equals("EN_PROCESO")) {
        throw new VentaYaFinalizadaException("venta finalizada.");
    }

// Verifico postre
    if (!abbPostres.containsKey(codigo)) {
        throw new PostreNoExisteException("El postre no está registrado.");
    }
  Postre postre = abbPostres.get(codigo);

  //Obtengo la orden a traves de la venta
  Orden ordenVenta = ventaEncontrada.getOrden();

  // Verificar límite 40
    int cantidadActual = ordenVenta.calcularCantTotal();

  if (cantidadActual + cantidad > 40) {
        throw new CantidadInvalidaException("No se puede superar 40 unidades por venta.");
    }
  
  ItemOrden existente = ordenVenta.find(codigo);
    if (existente != null) {
        int nuevaCantidad = existente.getCantidad() + cantidad;
        existente.setCantidad(nuevaCantidad);

} else {
      ItemOrden nuevo = new ItemOrden(cantidad, postre);
    ordenVenta.insBack(nuevo);
}
}

// REQUERIMIENTO 6
public void eliminarPostreDeVenta(String codigo, int cantidad, int numeroVenta)
        throws VentaNoExisteException, VentaYaFinalizadaException,
               PostreNoExisteException, CantidadInvalidaException {

    // Busco la venta
    Venta ventaEncontrada = null;
    int i = 0;

    while (i < listaVentas.size() && ventaEncontrada == null) {

        Venta v = listaVentas.get(i);

        if (v.getNumero() == numeroVenta) {
            ventaEncontrada = v;
        }

        i++;
    }

    if (ventaEncontrada == null) {
        throw new VentaNoExisteException("El numero de venta no existe.");
    }

    //Verifico estado
    if (!ventaEncontrada.getEstado().equals("EN_PROCESO")) {
        throw new VentaYaFinalizadaException("La orden ya fue pagada.");
    }

    //Busco el postre en la orden
    Orden ordenVenta = ventaEncontrada.getOrden();
    ItemOrden existente = ordenVenta.find(codigo);

    if (existente == null) {
        throw new PostreNoExisteException("El postre no existe en esta orden.");
    }

    if (cantidad > existente.getCantidad()) {
        throw new CantidadInvalidaException("Cantidad invalida.");
    }

    int nuevaCantidad = existente.getCantidad() - cantidad;
    existente.setCantidad(nuevaCantidad);

    //Si quedó en 0, eliminar el item completo
    if (nuevaCantidad == 0) {
        ordenVenta.deleteOrden(codigo);
    }

    //Resto monto
    //NO SE
}

// REQUERIMIENTO 7
public float finalizarVenta(int numeroVenta, boolean confirmar)
        throws VentaNoExisteException, VentaYaFinalizadaException {

//Busco la venta
Venta ventaEncontrada = null;
int i = 0;
while (i < listaVentas.size() && ventaEncontrada == null) {
     Venta v = listaVentas.get(i);
       if (v.getNumero() == numeroVenta) {
            ventaEncontrada = v;
        }

        i++;
    }
if (ventaEncontrada == null) {
        throw new VentaNoExisteException("El numero de venta no existe.");
    }

  // Verifico el estado si esta en prooceso o no
 if (!ventaEncontrada.getEstado().equals("EN_PROCESO")) {
      throw new VentaYaFinalizadaException("La venta ya fue pagada.");
    }

// Verifico si tiene postres, si cantTotal=0 es que no hay postres
    Orden ordenVenta = ventaEncontrada.getOrden();

    if (ordenVenta.calcularCantTotal() == 0) {

 // No tiene postres elimino venta
  listaVentas.remove(ventaEncontrada);
        return 0;
 }

//Si confirma
if (confirmar) {
  ventaEncontrada.setEstado("FINALIZADA");
      return ventaEncontrada.getMontoTotal();
}
else {
// Si cancela, elimino venta
    listaVentas.remove(ventaEncontrada);
      return 0;
    }
}

// REQUERIMIENTO 8
public void listadoVentas(char letra) {

  for (int i = 0; i < listaVentas.size(); i++) {
       Venta v = listaVentas.get(i);
 // Si la indicación es T → mostrar todas
        if (letra == 'T') {
System.out.println(
                "Numero: " + v.getNumero() +
                " Fecha: " + v.getFecha() +
                " Direccion: " + v.getDireccionEntrega() +
                " Monto: " + v.getMontoTotal() +
                " Estado: " + v.getEstado()
            );
        }

  // Si la indicación es P=solo en proceso
        else if (letra == 'P') {
             if (v.getEstado().equals("EN_PROCESO")) {

                System.out.println(
                    "Numero: " + v.getNumero() +
                    " Fecha: " + v.getFecha() +
                    " Direccion: " + v.getDireccionEntrega() +
                    " Monto: " + v.getMontoTotal() +
                    " Estado: " + v.getEstado()
                );
            }
        }

   // Si la indicación es F=solo finalizadas
        else if (letra == 'F') {
          if (v.getEstado().equals("FINALIZADA")) {
              System.out.println(
                    "Numero: " + v.getNumero() +
                    " Fecha: " + v.getFecha() +
                    " Direccion: " + v.getDireccionEntrega() +
                    " Monto: " + v.getMontoTotal() +
                    " Estado: " + v.getEstado()
                );
            }
        }
    }
}

// REQUERIMIENTO 9
public void listadoPostresDeVenta(int numeroVenta)
        throws VentaNoExisteException {

    // Busco la venta
    Venta ventaEncontrada = null;

    for (int i = 0; i < listaVentas.size(); i++) {
        Venta v = listaVentas.get(i);

        if (v.getNumero() == numeroVenta) {
            ventaEncontrada = v;
            break;
        }
    }

    //Si no existe = error
    if (ventaEncontrada == null) {
        throw new VentaNoExisteException("El número de venta no existe.");
    }

    //Obtener la orden
    Orden ordenVenta = ventaEncontrada.getOrden();

    //Recorro todos los postres de una orden y asi sucesivamente
  for (int i = 0; i < ordenVenta.getTope(); i++) {
      //cada item tiene postre y cant
        ItemOrden item = ordenVenta.getItem(i);
        Postre p = item.getPostre();

System.out.println(
                "Codigo: " + p.getCodigo() +
                " Nombre: " + p.getNombre() +
                " Precio: " + p.getPrecio() +
                " Tipo: " + p.getTipoPostre() +
                " Cantidad: " + item.getCantidad()
        );
    }
}
