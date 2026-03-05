package logica;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import logica.excepciones.*;
import logica.vo.*;

public class CapaLogica extends UnicastRemoteObject implements IFachada, Serializable {

    private static final long serialVersionUID = 1L;
 
    private TreeMap<String, Postre> abbPostres;
    private ArrayList<Venta> listaVentas;

   
    private transient Monitor monitor;
    private transient Persistencia persistencia;
    private String nomArchivo; 

   
    public CapaLogica() throws RemoteException {
        abbPostres = new TreeMap<>();
        listaVentas = new ArrayList<>();
        monitor = new Monitor();
        persistencia = new Persistencia();
        nomArchivo = cargarNombreArchivo();
    }

    private String cargarNombreArchivo() {
        Properties props = new Properties();
        try (InputStream is =
                 CapaLogica.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
                return props.getProperty("nomArchivo", "datos.dat");
            }
        } catch (IOException ignored) {}
        return "datos.dat";
    }

    //  REQ 1: Alta postre ────────────────────────────
    public void altaPostre(VOPostreAlta vo)
            throws RemoteException, PostreYaExisteException,
                   CodigoInvalidoException, PrecioInvalidoException {

        monitor.comienzoEscritura();
        try {
            String codigo = vo.getCodigo();
            String nombre = vo.getNombre();
            float  precio = vo.getPrecio();

            if (codigo == null || !codigo.matches("[a-zA-Z0-9]+"))
                throw new CodigoInvalidoException("Código inválido: debe ser alfanumérico.");

            if (precio <= 0)
                throw new PrecioInvalidoException("El precio debe ser mayor a 0.");

            if (abbPostres.containsKey(codigo))
                throw new PostreYaExisteException("Ya existe un postre con código: " + codigo);

            Postre nuevo;
            if (vo.isLight()) {
                nuevo = new Light(codigo, nombre, precio, vo.getTipoEndulzante(), vo.getDescripcion());
            } else {
                nuevo = new Postre(codigo, nombre, precio);
            }
            abbPostres.put(codigo, nuevo);
        } finally {
            monitor.terminoEscritura();
        }
    }

    // ─ REQ 2: Listado general de postres ─────────
    public ArrayList<VOPostreListado> listadoGeneralPostres() throws RemoteException {
        monitor.comienzoLectura();
        try {
            ArrayList<VOPostreListado> lista = new ArrayList<>();
            for (Postre p : abbPostres.values()) {
                lista.add(new VOPostreListado(
                        p.getCodigo(), p.getNombre(), p.getPrecio(), p.getTipoPostre()));
            }
            return lista;
        } finally {
            monitor.terminoLectura();
        }
    }

    // ── REQ 3: Detalle de un postre ─────────────────
    public VOPostreAlta listadoDetalladoPostre(String codigo)
            throws RemoteException, PostreNoExisteException {
        monitor.comienzoLectura();
        try {
            if (!abbPostres.containsKey(codigo))
                throw new PostreNoExisteException("El postre no está registrado: " + codigo);

            Postre p = abbPostres.get(codigo);
            if (p instanceof Light) {
                Light l = (Light) p;
                return new VOPostreAlta(l.getCodigo(), l.getNombre(), l.getPrecio(),"LIGHT", l.getTipoEndulzante(), l.getDescripcion());
            } else {
                return new VOPostreAlta(p.getCodigo(), p.getNombre(), p.getPrecio(), "NORMAL");
            }
        } finally {
            monitor.terminoLectura();
        }
    }

    //  REQ 4: Comenzar venta ────────────────────────────────────────────────
    public void comenzarVenta(LocalDate fecha, String direccion)
            throws RemoteException, FechaInvalidaException {
        monitor.comienzoEscritura();
        try {
            if (!listaVentas.isEmpty()) {
                Venta ultima = listaVentas.get(listaVentas.size() - 1);
                if (fecha.isBefore(ultima.getFecha())) {
                    throw new FechaInvalidaException(
                            "La fecha ingresada es menor que la última fecha registrada.");
                }
            }
            Venta nueva = new Venta(fecha, direccion);
            listaVentas.add(nueva);
        } finally {
            monitor.terminoEscritura();
        }
    }

    //  REQ 5: Agregar postre a venta ────────────────────────────────────────
    public void agregarPostreAVenta(VOPostreVenta vo)
            throws RemoteException, VentaNoExisteException,
                   VentaYaFinalizadaException, PostreNoExisteException,
                   CantidadInvalidaException {
        monitor.comienzoEscritura();
        try {
            String codigo = vo.getCodigo();
            int    cantidad = vo.getCantidad();
            int    numVenta = vo.getNumVenta();

            Venta venta = buscarVenta(numVenta);

            if (!venta.getEstado().equals("EN_PROCESO"))
                throw new VentaYaFinalizadaException("La venta ya fue finalizada.");

            if (!abbPostres.containsKey(codigo))
                throw new PostreNoExisteException("El postre no existe: " + codigo);

            Postre postre = abbPostres.get(codigo);
            Orden  orden = venta.getOrden();
            int    cantActual = orden.calcularCantTotal();

            if (cantActual + cantidad > 40)
                throw new CantidadInvalidaException(
                    "No se puede superar 40 unidades por venta. Disponibles: " + (40 - cantActual));

            ItemOrden existente = orden.find(codigo);
            if (existente != null) {
                existente.setCantidad(existente.getCantidad() + cantidad);
            } else {
            	ItemOrden nuevoItem = new ItemOrden(cantidad, postre);
                orden.insBack(nuevoItem);
            }
        } finally {
            monitor.terminoEscritura();
        }
    }

    // ─ REQ 6: Eliminar postre de venta ────────────────────────────
    public void eliminarPostreDeVenta(VOPostreVenta vo)
            throws RemoteException, VentaNoExisteException,
                   VentaYaFinalizadaException, PostreNoExisteException,
                   CantidadInvalidaException {
        monitor.comienzoEscritura();
        try {
            String codigo   = vo.getCodigo();
            int cantidad = vo.getCantidad();
            int numVenta = vo.getNumVenta();

            Venta venta = buscarVenta(numVenta);

            if (!venta.getEstado().equals("EN_PROCESO"))
                throw new VentaYaFinalizadaException("La venta ya fue finalizada.");

            Orden orden = venta.getOrden();
            ItemOrden existente = orden.find(codigo);

            if (existente == null)
                throw new PostreNoExisteException("El postre no está en la orden: " + codigo);

            if (cantidad > existente.getCantidad())
                throw new CantidadInvalidaException(
                    "Cantidad a eliminar mayor a la existente en la orden.");

            int nuevaCantidad = existente.getCantidad() - cantidad;
            existente.setCantidad(nuevaCantidad);

            if (nuevaCantidad == 0)
                orden.deleteOrden(codigo);
        } finally {
            monitor.terminoEscritura();
        }
    }

    // ── REQ 7: Finalizar venta ────────────────────
    public float finalizarVenta(int numVenta, boolean confirmar)
            throws RemoteException, VentaNoExisteException, VentaYaFinalizadaException {
        monitor.comienzoEscritura();
        try {
            Venta venta = buscarVenta(numVenta);

            if (!venta.getEstado().equals("EN_PROCESO"))
                throw new VentaYaFinalizadaException("La venta ya fue finalizada.");

            Orden orden = venta.getOrden();
            if (orden.calcularCantTotal() == 0) {
                listaVentas.remove(venta);
                return 0;
            }

            if (confirmar) {
                venta.setEstado("FINALIZADA");
                return venta.getMontoTotal();
            } else {
                listaVentas.remove(venta);
                return 0;
            }
        } finally {
            monitor.terminoEscritura();
        }
    }

    // ── REQ 8: Listado ventas ────────────
    public ArrayList<VOVenta> listadoVentas(char tipo) throws RemoteException {
        monitor.comienzoLectura();
        try {
            ArrayList<VOVenta> lista = new ArrayList<>();
            for (Venta v : listaVentas) {
                boolean incluir = (tipo == 'T')
                        || (tipo == 'P' && v.getEstado().equals("EN_PROCESO"))
                        || (tipo == 'F' && v.getEstado().equals("FINALIZADA"));
                if (incluir) {
                    lista.add(new VOVenta(
                            v.getNumero(),
                            v.getFecha().toString(),
                            v.getDireccionEntrega(),
                            v.getEstado(),
                            v.getMontoTotal()));
                }
            }
            return lista;
        } finally {
            monitor.terminoLectura();
        }
    }

    // ── REQ 9: Listado postres de una venta ────────────────
    public ArrayList<VOItemOrden> listadoPostresDeVenta(int numVenta)
            throws RemoteException, VentaNoExisteException {
        monitor.comienzoLectura();
        try {
            Venta venta = buscarVenta(numVenta);
            Orden orden = venta.getOrden();
            ArrayList<VOItemOrden> lista = new ArrayList<>();
            for (int i = 0; i < orden.getTope(); i++) {
                ItemOrden item = orden.getItem(i);
                Postre p = item.getPostre();
                lista.add(new VOItemOrden(
                        venta.getNumero(),        
                        p.getCodigo(), 
                        p.getNombre(),
                        p.getPrecio(),           
                        p.getTipoPostre(),        
                        item.getCantidad()        
                ));
            }
            return lista;
        } finally {
            monitor.terminoLectura();
        }
    }

    // ── REQ 10: Total ventas de un postre en una fecha ────────────────────
    public VORecaudacion totalVentasPostreEnFecha(String codigoPostre, LocalDate fecha)
            throws RemoteException, PostreNoExisteException {
        monitor.comienzoLectura();
        try {
            if (!abbPostres.containsKey(codigoPostre))
                throw new PostreNoExisteException("El postre no existe: " + codigoPostre);

            float montoTotal    = 0;
            int   cantidadTotal = 0;

            for (Venta v : listaVentas) {
                if (v.getEstado().equals("FINALIZADA") && v.getFecha().equals(fecha)) {
                    Orden orden = v.getOrden();
                    for (int i = 0; i < orden.getTope(); i++) {
                        ItemOrden item = orden.getItem(i);
                        if (item.getPostre().getCodigo().equals(codigoPostre)) {
                            cantidadTotal += item.getCantidad();
                            montoTotal    += item.calcularSubtotal();
                        }
                    }
                }
            }
            return new VORecaudacion(montoTotal, cantidadTotal);
        } finally {
            monitor.terminoLectura();
        }
    }

    // ─ REQ 11: Respaldar datos ──────

    public void respaldarDatos() throws RemoteException, PersistenciaException {
        monitor.comienzoLectura();
        try {
            persistencia.respaldar(nomArchivo, this);
        } finally {
            monitor.terminoLectura();
        }
    }

    //  REQ 12: Recuperar datos ───────────────
    public void recuperarDatos() throws RemoteException, PersistenciaException {
        monitor.comienzoEscritura();
        try {
            CapaLogica aux = persistencia.recuperar(nomArchivo);
            this.abbPostres  = aux.abbPostres;
            this.listaVentas = aux.listaVentas;
            this.monitor = new Monitor();
            this.persistencia = new Persistencia();
            
            if (!listaVentas.isEmpty()) {
                int maxNum = listaVentas.stream()
                        .mapToInt(Venta::getNumero).max().orElse(0);
                Venta.setContadorNumero(maxNum + 1);
            }
        } finally {
            monitor.terminoEscritura();
        }
    }

    public void cargarDatos() {
        try {
            recuperarDatos();
            System.out.println("Datos cargados desde: " + nomArchivo);
        } catch (Exception e) {
            System.out.println("No se encontraron datos previos. Iniciando vacío.");
        }
    }
    
    
    private Venta buscarVenta(int numVenta)
            throws VentaNoExisteException {

        for (Venta v : listaVentas) {
            if (v.getNumero() == numVenta) {
                return v;
            }
        }

        throw new VentaNoExisteException("La venta no existe.");
    }


}