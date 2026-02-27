package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import logica.vo.VOPostreAlta;
import logica.vo.VOPostreListado;
import logica.vo.VOPostreVenta;
import logica.vo.VORecaudacion;
import logica.vo.VOVenta;
import logica.excepciones.*;

public interface IFachada extends Remote {

    // REQ 1 - Alta postre
    void altaPostre(VOPostreAlta vo)
            throws RemoteException,
                   PostreYaExisteException,
                   CodigoInvalidoException,
                   PrecioInvalidoException;

    // REQ 2 - Listado general
    ArrayList<VOPostreListado> listadoGeneralPostres()
            throws RemoteException;

    // REQ 3 - Detalle postre
    VOPostreAlta listadoDetalladoPostre(String codigo)
            throws RemoteException,
                   PostreNoExisteException;

    // REQ 4 - Comenzar venta
    void comenzarVenta(LocalDate fecha, String direccion)
            throws RemoteException,
                   FechaInvalidaException;

    // REQ 5 - Agregar postre a venta
    void agregarPostreAVenta(VOPostreVenta vo)
            throws RemoteException,
                   VentaNoExisteException,
                   VentaYaFinalizadaException,
                   PostreNoExisteException,
                   CantidadInvalidaException;

    // 6 - Eliminar postre de venta
    void eliminarPostreDeVenta(VOPostreVenta vo)
            throws RemoteException,
                   VentaNoExisteException,
                   VentaYaFinalizadaException,
                   PostreNoExisteException,
                   CantidadInvalidaException;

    // 7 - Finalizar venta
    float finalizarVenta(int numVenta, boolean confirmada)
            throws RemoteException,
                   VentaNoExisteException,
                   VentaYaFinalizadaException;

    // 8 - Listado ventas (T, P o F)
    ArrayList<VOVenta> listadoVentas(char tipo)
            throws RemoteException;

    // 9 - Listado postres de una venta
    ArrayList<VOPostreVenta> listadoPostresDeVenta(int numVenta)
            throws RemoteException,
                   VentaNoExisteException;

    // 10 - Total vendido por postre en fecha
    VORecaudacion totalVentasPostreEnFecha(String codigoPostre, LocalDate fecha)
            throws RemoteException,
                   PostreNoExisteException;

    // 11 - Respaldo de datos
    void respaldarDatos()
            throws RemoteException,
                   PersistenciaException;

    // 12 - Recuperación de datos
    void recuperarDatos()
            throws RemoteException,
                   PersistenciaException;
}