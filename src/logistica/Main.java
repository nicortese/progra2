package logistica;

import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import logistica.tipos.Alimentos;
import logistica.tipos.ContenidoPaquete;
import logistica.tipos.Electronica;
import logistica.tipos.Fragiles;

/**
 * Punto de entrada: menú de consola para operar {@link CentroDistribucion} y {@link Camion}.
 */
public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);
        CentroDistribucion<ContenidoPaquete> centro = new CentroDistribucion<>();
        Camion<ContenidoPaquete> camion = new Camion<>();
        Optional<Paquete<ContenidoPaquete>> listoParaCamion = Optional.empty();

        System.out.println("--- Logística: centro de distribución y camión ---");

        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("1) Ingresar paquete al centro");
            System.out.println("2) Procesar siguiente del centro (prioridad + FIFO)");
            System.out.println("3) Cargar al camión el último paquete procesado");
            System.out.println("4) Descargar del camión (LIFO)");
            System.out.println("5) Deshacer última carga del camión");
            System.out.println("6) Ver estado");
            System.out.println("0) Salir");
            System.out.print("Opción: ");

            String linea = in.nextLine().trim();
            try {
                int op = Integer.parseInt(linea);
                switch (op) {
                    case 1 -> listoParaCamion = ingresarPaqueteCentro(in, centro, listoParaCamion);
                    case 2 -> listoParaCamion = procesarSiguiente(centro, listoParaCamion);
                    case 3 -> listoParaCamion = cargarCamion(camion, listoParaCamion);
                    case 4 -> descargarCamion(camion);
                    case 5 -> deshacerCamion(camion);
                    case 6 -> mostrarEstado(centro, camion, listoParaCamion);
                    case 0 -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número de opción.");
            }
        }
        System.out.println("Fin.");
    }

    private static Optional<Paquete<ContenidoPaquete>> ingresarPaqueteCentro(
            Scanner in,
            CentroDistribucion<ContenidoPaquete> centro,
            Optional<Paquete<ContenidoPaquete>> listoParaCamion) {
        try {
            System.out.print("Peso (kg): ");
            double peso = Double.parseDouble(in.nextLine().trim());
            System.out.print("Destino: ");
            String destino = in.nextLine();
            System.out.print("¿Urgente? (s/n): ");
            boolean urgente = esSi(in.nextLine());

            ContenidoPaquete contenido = leerContenidoSegunTipo(in);
            Paquete<ContenidoPaquete> p = new Paquete<>(peso, destino, contenido, urgente);
            centro.ingresar(p);
            System.out.println("Ingresado al centro: " + p);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return listoParaCamion;
    }

    /**
     * Pide el tipo de paquete (contenido) y los datos específicos de cada clase.
     */
    private static ContenidoPaquete leerContenidoSegunTipo(Scanner in) {
        System.out.println("Tipo de contenido del paquete:");
        System.out.println("  1) Electrónica");
        System.out.println("  2) Alimentos");
        System.out.println("  3) Frágiles");
        System.out.print("Elegir (1-3): ");
        int tipo = Integer.parseInt(in.nextLine().trim());
        switch (tipo) {
            case 1:
                System.out.print("Descripción: ");
                String descE = in.nextLine();
                System.out.print("Valor asegurado: ");
                double valor = Double.parseDouble(in.nextLine().trim());
                return new Electronica(descE, valor);
            case 2:
                System.out.print("Nombre del producto: ");
                String nombre = in.nextLine();
                System.out.print("¿Requiere frío? (s/n): ");
                boolean frio = esSi(in.nextLine());
                return new Alimentos(nombre, frio);
            case 3:
                System.out.print("Descripción: ");
                String descF = in.nextLine();
                System.out.print("Nivel de cuidado (entero): ");
                int nivel = Integer.parseInt(in.nextLine().trim());
                return new Fragiles(descF, nivel);
            default:
                throw new IllegalArgumentException("Tipo inválido: elegí 1, 2 o 3.");
        }
    }

    private static Optional<Paquete<ContenidoPaquete>> procesarSiguiente(
            CentroDistribucion<ContenidoPaquete> centro,
            Optional<Paquete<ContenidoPaquete>> listoParaCamion) {
        if (listoParaCamion.isPresent()) {
            System.out.println("Ya hay un paquete procesado esperando. Cargalo al camión (3) o dejalo así.");
            return listoParaCamion;
        }
        Optional<Paquete<ContenidoPaquete>> p = centro.retirarSiguienteParaProcesar();
        if (p.isEmpty()) {
            System.out.println("No hay paquetes pendientes en el centro.");
            return Optional.empty();
        }
        System.out.println("Procesado: " + p.get());
        return p;
    }

    private static Optional<Paquete<ContenidoPaquete>> cargarCamion(
            Camion<ContenidoPaquete> camion,
            Optional<Paquete<ContenidoPaquete>> listoParaCamion) {
        if (listoParaCamion.isEmpty()) {
            System.out.println("No hay paquete procesado listo. Usá la opción 2 primero.");
            return listoParaCamion;
        }
        Paquete<ContenidoPaquete> p = listoParaCamion.get();
        camion.cargar(p);
        System.out.println("Cargado al camión: " + p);
        return Optional.empty();
    }

    private static void descargarCamion(Camion<ContenidoPaquete> camion) {
        camion.descargar().ifPresentOrElse(
                p -> System.out.println("Descargado: " + p),
                () -> System.out.println("El camión está vacío."));
    }

    private static void deshacerCamion(Camion<ContenidoPaquete> camion) {
        camion.deshacerUltimaCarga().ifPresentOrElse(
                p -> System.out.println("Se quitó la última carga: " + p),
                () -> System.out.println("No hay nada que deshacer en el camión."));
    }

    private static void mostrarEstado(
            CentroDistribucion<ContenidoPaquete> centro,
            Camion<ContenidoPaquete> camion,
            Optional<Paquete<ContenidoPaquete>> listoParaCamion) {
        System.out.println("--- Estado ---");
        System.out.println("Pendientes en centro: " + centro.cantidadPendiente());
        centro.verSiguienteParaProcesar().ifPresentOrElse(
                p -> System.out.println("Próximo en centro (sin sacar): " + p),
                () -> System.out.println("Próximo en centro: (ninguno)"));
        System.out.println("Paquetes en camión: " + camion.cantidadCargada());
        camion.verProximoADescargar().ifPresentOrElse(
                p -> System.out.println("Tope del camión (sale primero): " + p),
                () -> System.out.println("Camión vacío."));
        listoParaCamion.ifPresentOrElse(
                p -> System.out.println("Listo para cargar al camión: " + p),
                () -> System.out.println("Listo para camión: (ninguno)"));
    }

    private static boolean esSi(String linea) {
        if (linea == null) {
            return false;
        }
        String s = linea.trim().toLowerCase(Locale.ROOT);
        return s.equals("s") || s.equals("si") || s.equals("sí") || s.equals("y") || s.equals("yes");
    }
}
