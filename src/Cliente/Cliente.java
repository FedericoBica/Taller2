package Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import logica.IFachada;
import logica.vo.*;
import logica.excepciones.*;

public class Cliente {

    private static IFachada fachada;

    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream is =
                 Cliente.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            System.err.println("No se pudo leer config.properties: " + e.getMessage());
        }

        String ip     = props.getProperty("ipServidor",    "127.0.0.1");
        int    puerto = Integer.parseInt(props.getProperty("puertoServidor", "1099"));
        try {
            String url = "rmi://" + ip + ":" + puerto + "/Fachada";
            fachada = (IFachada) Naming.lookup(url);
            System.out.println("Conectado al servidor en " + url);
            System.out.println("=".repeat(60));

            probarReq1();
            probarReq2();
            probarReq3();
            probarReq4();
            probarReq5();
            probarReq6();
            probarReq7();
            probarReq8();
            probarReq9();
            probarReq10();
            probarReq11();
            probarReq12();

            System.out.println("\n" + "=".repeat(60));
            System.out.println("TODAS LAS PRUEBAS COMPLETADAS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REQ 1: Alta postre
    private static void probarReq1() throws Exception {
        titulo("REQ 1 - Alta Postre");

        // Alta normal
        fachada.altaPostre(new VOPostreAlta("P001", "Flan", 120.0f, "NORMAL"));
        System.out.println("[OK] Postre NORMAL P001 'Flan' agregado.");

        // Alta light
        fachada.altaPostre(new VOPostreAlta("P002", "Mousse Light", 150.0f,
                "LIGHT", "Stevia", "Sin azúcar añadida"));
        System.out.println("[OK] Postre LIGHT P002 'Mousse Light' agregado.");

        // Error: código ya existe
        try {
            fachada.altaPostre(new VOPostreAlta("P001", "Otro", 50.0f, "NORMAL"));
            System.out.println("[FAIL] Debería lanzar PostreYaExisteException");
        } catch (PostreYaExisteException e) {
            System.out.println("[OK] PostreYaExisteException: " + e.darMensaje());
        }

        // Error: código inválido
        try {
            fachada.altaPostre(new VOPostreAlta("P 01!", "Inválido", 50.0f, "NORMAL"));
            System.out.println("[FAIL] Debería lanzar CodigoInvalidoException");
        } catch (CodigoInvalidoException e) {
            System.out.println("[OK] CodigoInvalidoException: " + e.darMensaje());
        }

        // Error: precio inválido
        try {
            fachada.altaPostre(new VOPostreAlta("P003", "Gratis", -1.0f, "NORMAL"));
            System.out.println("[FAIL] Debería lanzar PrecioInvalidoException");
        } catch (PrecioInvalidoException e) {
            System.out.println("[OK] PrecioInvalidoException: " + e.darMensaje());
        }

        // Más postres para pruebas posteriores
        fachada.altaPostre(new VOPostreAlta("P003", "Torta Choco", 200.0f, "NORMAL"));
        fachada.altaPostre(new VOPostreAlta("P004", "Tiramisú", 180.0f, "NORMAL"));
    }

    //  REQ 2: Listado general 
    private static void probarReq2() throws Exception {
        titulo("REQ 2 - Listado General de Postres");
        ArrayList<VOPostreListado> lista = fachada.listadoGeneralPostres();
        System.out.println("Total postres: " + lista.size());
        for (VOPostreListado vo : lista) {
            System.out.printf("  %-6s %-20s $%-8.2f %s%n",
                    vo.getCodigo(), vo.getNombre(), vo.getPrecio(), vo.getTipo());
        }
    }

    //  REQ 3: Detalle postre
    private static void probarReq3() throws Exception {
        titulo("REQ 3 - Detalle de Postre");

        VOPostreAlta normal = fachada.listadoDetalladoPostre("P001");
        System.out.println("[OK] Código: " + normal.getCodigo() +
                           " | Nombre: " + normal.getNombre() +
                           " | Tipo: " + normal.getTipo());

        VOPostreAlta light = fachada.listadoDetalladoPostre("P002");
        System.out.println("[OK] Código: " + light.getCodigo() +
                           " | Endulzante: " + light.getTipoEndulzante() +
                           " | Desc: " + light.getDescripcion());

        try {
            fachada.listadoDetalladoPostre("NOEXISTE");
            System.out.println("[FAIL] Debería lanzar PostreNoExisteException");
        } catch (PostreNoExisteException e) {
            System.out.println("[OK] PostreNoExisteException: " + e.darMensaje());
        }
    }

    //  REQ 4: Comenzar venta 
    private static void probarReq4() throws Exception {
        titulo("REQ 4 - Comenzar Venta");

        fachada.comenzarVenta(LocalDate.of(2025, 6, 1), "Av. 18 de Julio 100");
        System.out.println("[OK] Venta 1 creada.");

        fachada.comenzarVenta(LocalDate.of(2025, 6, 1), "Bulevar España 200");
        System.out.println("[OK] Venta 2 creada (misma fecha, ok).");

        fachada.comenzarVenta(LocalDate.of(2025, 6, 5), "Rambla 300");
        System.out.println("[OK] Venta 3 creada (fecha posterior).");

        try {
            fachada.comenzarVenta(LocalDate.of(2025, 1, 1), "Atrasada");
            System.out.println("[FAIL] Debería lanzar FechaInvalidaException");
        } catch (FechaInvalidaException e) {
            System.out.println("[OK] FechaInvalidaException: " + e.darMensaje());
        }
    }

    //  REQ 5: Agregar postre a venta 
    private static void probarReq5() throws Exception {
        titulo("REQ 5 - Agregar Postre a Venta");

        fachada.agregarPostreAVenta(new VOPostreVenta("P001", 5, 1));
        System.out.println("[OK] 5x P001 agregados a venta 1.");

        fachada.agregarPostreAVenta(new VOPostreVenta("P001", 3, 1));
        System.out.println("[OK] 3x P001 más a venta 1 (acumula, total 8).");

        fachada.agregarPostreAVenta(new VOPostreVenta("P002", 10, 1));
        System.out.println("[OK] 10x P002 a venta 1.");

        try {
            fachada.agregarPostreAVenta(new VOPostreVenta("P001", 30, 1));
            System.out.println("[FAIL] Debería lanzar CantidadInvalidaException (límite 40)");
        } catch (CantidadInvalidaException e) {
            System.out.println("[OK] CantidadInvalidaException: " + e.darMensaje());
        }

        try {
            fachada.agregarPostreAVenta(new VOPostreVenta("P001", 5, 999));
            System.out.println("[FAIL] Debería lanzar VentaNoExisteException");
        } catch (VentaNoExisteException e) {
            System.out.println("[OK] VentaNoExisteException: " + e.darMensaje());
        }

        // Venta 2: agregar postres que luego se finalizarán para req 10
        fachada.agregarPostreAVenta(new VOPostreVenta("P003", 2, 2));
        fachada.agregarPostreAVenta(new VOPostreVenta("P004", 3, 2));

        // Venta 3: para prueba de listado
        fachada.agregarPostreAVenta(new VOPostreVenta("P001", 1, 3));
    }

    //  REQ 6: Eliminar postre de venta
    private static void probarReq6() throws Exception {
        titulo("REQ 6 - Eliminar Postre de Venta");

        fachada.eliminarPostreDeVenta(new VOPostreVenta("P001", 3, 1));
        System.out.println("[OK] 3x P001 eliminados de venta 1 (quedan 5).");

        // Eliminar toda la cantidad → debe quitarse el item
        fachada.eliminarPostreDeVenta(new VOPostreVenta("P002", 10, 1));
        System.out.println("[OK] 10x P002 eliminados (item removido de venta 1).");

        try {
            fachada.eliminarPostreDeVenta(new VOPostreVenta("P001", 100, 1));
            System.out.println("[FAIL] Debería lanzar CantidadInvalidaException");
        } catch (CantidadInvalidaException e) {
            System.out.println("[OK] CantidadInvalidaException: " + e.darMensaje());
        }

        try {
            fachada.eliminarPostreDeVenta(new VOPostreVenta("P002", 1, 1));
            System.out.println("[FAIL] Debería lanzar PostreNoExisteException (no está en orden)");
        } catch (PostreNoExisteException e) {
            System.out.println("[OK] PostreNoExisteException (no en orden): " + e.darMensaje());
        }
    }

    // REQ 7: Finalizar venta
    private static void probarReq7() throws Exception {
        titulo("REQ 7 - Finalizar Venta");

        // Confirmar venta 1 (tiene postres)
        float monto1 = fachada.finalizarVenta(1, true);
        System.out.printf("[OK] Venta 1 finalizada. Monto: $%.2f%n", monto1);

        // Confirmar venta 2 (tiene postres)
        float monto2 = fachada.finalizarVenta(2, true);
        System.out.printf("[OK] Venta 2 finalizada. Monto: $%.2f%n", monto2);

        // Cancelar venta 3 (elimina)
        float monto3 = fachada.finalizarVenta(3, false);
        System.out.printf("[OK] Venta 3 cancelada. Monto: $%.2f%n", monto3);

        // Nueva venta vacía: debe eliminarse automáticamente
        fachada.comenzarVenta(LocalDate.of(2025, 6, 10), "Sin postres");
        float monto4 = fachada.finalizarVenta(4, true);
        System.out.printf("[OK] Venta 4 vacía eliminada automáticamente. Monto: $%.2f%n", monto4);

        // Error: venta ya finalizada
        try {
            fachada.finalizarVenta(1, true);
            System.out.println("[FAIL] Debería lanzar VentaYaFinalizadaException");
        } catch (VentaYaFinalizadaException e) {
            System.out.println("[OK] VentaYaFinalizadaException: " + e.darMensaje());
        }
    }

    //  REQ 8: Listado ventas 
    private static void probarReq8() throws Exception {
        titulo("REQ 8 - Listado de Ventas");

        System.out.println("--- TODAS (T) ---");
        imprimirVentas(fachada.listadoVentas('T'));

        System.out.println("--- EN PROCESO (P) ---");
        imprimirVentas(fachada.listadoVentas('P'));

        System.out.println("--- FINALIZADAS (F) ---");
        imprimirVentas(fachada.listadoVentas('F'));
    }

    //  REQ 9: Listado postres de una venta
    private static void probarReq9() throws Exception {
        titulo("REQ 9 - Postres de una Venta");

        System.out.println("Postres de venta 1:");
        ArrayList<VOItemOrden> lista = fachada.listadoPostresDeVenta(1);
        for (VOItemOrden vo : lista) {
            System.out.printf("  %-6s %-20s $%-8.2f %-6s x%d%n",
                    vo.getCodigo(), vo.getNombre(), vo.getPrecio(),
                    vo.getTipo(), vo.getCantUnidades());
        }

        try {
            fachada.listadoPostresDeVenta(999);
            System.out.println("[FAIL] Debería lanzar VentaNoExisteException");
        } catch (VentaNoExisteException e) {
            System.out.println("[OK] VentaNoExisteException: " + e.darMensaje());
        }
    }

    //  REQ 10: Total ventas postre en fecha
    private static void probarReq10() throws Exception {
        titulo("REQ 10 - Total Ventas de Postre en Fecha");

        VORecaudacion rec = fachada.totalVentasPostreEnFecha("P003", LocalDate.of(2025, 6, 1));
        System.out.printf("[OK] P003 en 2025-06-01 → Cant: %d | Monto: $%.2f%n",
                rec.getCantUnidades(), rec.getMontoTotal());

        VORecaudacion rec2 = fachada.totalVentasPostreEnFecha("P001", LocalDate.of(2025, 6, 1));
        System.out.printf("[OK] P001 en 2025-06-01 → Cant: %d | Monto: $%.2f%n",
                rec2.getCantUnidades(), rec2.getMontoTotal());

        try {
            fachada.totalVentasPostreEnFecha("NOEXISTE", LocalDate.now());
            System.out.println("[FAIL] Debería lanzar PostreNoExisteException");
        } catch (PostreNoExisteException e) {
            System.out.println("[OK] PostreNoExisteException: " + e.darMensaje());
        }
    }

    //REQ 11: Respaldar datos 
    private static void probarReq11() throws Exception {
        titulo("REQ 11 - Respaldar Datos");
        fachada.respaldarDatos();
        System.out.println("[OK] Datos respaldados correctamente.");
    }

    //  REQ 12: Recuperar datos
    private static void probarReq12() throws Exception {
        titulo("REQ 12 - Recuperar Datos");
        fachada.recuperarDatos();
        System.out.println("[OK] Datos recuperados. Verificando postres:");
        ArrayList<VOPostreListado> lista = fachada.listadoGeneralPostres();
        System.out.println("  Postres tras recuperar: " + lista.size());
    }

  
    private static void titulo(String titulo) {
        System.out.println("\n" + "─".repeat(60));
        System.out.println("  " + titulo);
        System.out.println("─".repeat(60));
    }

    private static void imprimirVentas(ArrayList<VOVenta> ventas) {
        if (ventas.isEmpty()) {
            System.out.println("  (sin resultados)");
            return;
        }
        for (VOVenta v : ventas) {
            System.out.printf("  #%d | %s | %s | $%.2f | %s%n",
                    v.getNum(), v.getFecha(), v.getDireccionEntrega(),
                    v.getMontoTotal(), v.getEstado());
        }
    }
}