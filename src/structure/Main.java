package structure;

import java.util.Scanner;

import structure.definition.CamionADT;
import structure.definition.CentroDistribucionADT;
import structure.definition.ContenidoPaqueteADT;
import structure.definition.PaqueteADT;
import structure.implementation.AlimentosTF;
import structure.implementation.CamionTF;
import structure.implementation.CentroDistribucionTF;
import structure.implementation.ElectronicaTF;
import structure.implementation.FragilesTF;
import structure.implementation.PaqueteTF;

// Programa principal (menú de consola); no es TDA ni TF.

/**
 * Descripcion: Punto de entrada: menú para operar el centro de distribución y el camión.
 * Precondición: No tiene (clase solo con métodos estáticos).
 */
public class Main {

    /**
     * Descripcion: Impide instanciar Main (solo uso de métodos estáticos).
     * Precondición: No tiene.
     */
    private Main() {
    }

    /**
     * Descripcion: Crea centro y camión, muestra el menú en bucle hasta Salir; cierra el Scanner al terminar.
     * Precondición: No tiene (argumentos no usados).
     */
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            CentroDistribucionADT<ContenidoPaqueteADT> centro = new CentroDistribucionTF<>();
            CamionADT<ContenidoPaqueteADT> camion = new CamionTF<>();
            PaqueteADT<ContenidoPaqueteADT> listoParaCamion = null;

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
    }

    /**
     * Descripcion: Lee datos por teclado, crea un PaqueteTF y lo ingresa al centro.
     * Precondición: Scanner abierto; centro no nulo; devuelve el mismo listoParaCamion salvo que otra opción lo cambie.
     */
    private static PaqueteADT<ContenidoPaqueteADT> ingresarPaqueteCentro(
            Scanner in,
            CentroDistribucionADT<ContenidoPaqueteADT> centro,
            PaqueteADT<ContenidoPaqueteADT> listoParaCamion) {
        try {
            System.out.print("Peso (kg): ");
            double peso = Double.parseDouble(in.nextLine().trim());
            System.out.print("Destino: ");
            String destino = in.nextLine();
            System.out.print("¿Urgente? (s/n): ");
            boolean urgente = esSi(in.nextLine());

            ContenidoPaqueteADT contenido = leerContenidoSegunTipo(in);
            PaqueteTF<ContenidoPaqueteADT> p = new PaqueteTF<>(peso, destino, contenido, urgente);
            centro.ingresar(p);
            System.out.println("Ingresado al centro: " + p);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return listoParaCamion;
    }

    /**
     * Descripcion: Pide tipo de contenido (1 electrónica, 2 alimentos, 3 frágiles) y los datos; devuelve la instancia TF.
     * Precondición: Scanner abierto; entrada numérica válida donde corresponde.
     */
    private static ContenidoPaqueteADT leerContenidoSegunTipo(Scanner in) {
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
                return new ElectronicaTF(descE, valor);
            case 2:
                System.out.print("Nombre del producto: ");
                String nombre = in.nextLine();
                System.out.print("¿Requiere frío? (s/n): ");
                boolean frio = esSi(in.nextLine());
                return new AlimentosTF(nombre, frio);
            case 3:
                System.out.print("Descripción: ");
                String descF = in.nextLine();
                System.out.print("Nivel de cuidado (entero): ");
                int nivel = Integer.parseInt(in.nextLine().trim());
                return new FragilesTF(descF, nivel);
            default:
                throw new IllegalArgumentException("Tipo inválido: elegí 1, 2 o 3.");
        }
    }

    /**
     * Descripcion: Retira el siguiente paquete del centro y lo deja listo para el camión; avisa si ya había uno listo.
     * Precondición: centro no nulo.
     */
    private static PaqueteADT<ContenidoPaqueteADT> procesarSiguiente(
            CentroDistribucionADT<ContenidoPaqueteADT> centro,
            PaqueteADT<ContenidoPaqueteADT> listoParaCamion) {
        if (listoParaCamion != null) {
            System.out.println("Ya hay un paquete procesado esperando. Cargalo al camión (3) o dejalo así.");
            return listoParaCamion;
        }
        PaqueteADT<ContenidoPaqueteADT> p = centro.retirarSiguienteParaProcesar();
        if (p == null) {
            System.out.println("No hay paquetes pendientes en el centro.");
            return null;
        }
        System.out.println("Procesado: " + p);
        return p;
    }

    /**
     * Descripcion: Carga en el camión el paquete que quedó listo tras procesar; vacía la bandeja (devuelve nulo).
     * Precondición: camion no nulo.
     */
    private static PaqueteADT<ContenidoPaqueteADT> cargarCamion(
            CamionADT<ContenidoPaqueteADT> camion,
            PaqueteADT<ContenidoPaqueteADT> listoParaCamion) {
        if (listoParaCamion == null) {
            System.out.println("No hay paquete procesado listo. Usá la opción 2 primero.");
            return null;
        }
        camion.cargar(listoParaCamion);
        System.out.println("Cargado al camión: " + listoParaCamion);
        return null;
    }

    /**
     * Descripcion: Descarga un paquete del camión (LIFO) y muestra el resultado.
     * Precondición: camion no nulo.
     */
    private static void descargarCamion(CamionADT<ContenidoPaqueteADT> camion) {
        PaqueteADT<ContenidoPaqueteADT> p = camion.descargar();
        if (p != null) {
            System.out.println("Descargado: " + p);
        } else {
            System.out.println("El camión está vacío.");
        }
    }

    /**
     * Descripcion: Quita la última carga del camión (misma operación de pila que descargar en esta implementación).
     * Precondición: camion no nulo.
     */
    private static void deshacerCamion(CamionADT<ContenidoPaqueteADT> camion) {
        PaqueteADT<ContenidoPaqueteADT> p = camion.deshacerUltimaCarga();
        if (p != null) {
            System.out.println("Se quitó la última carga: " + p);
        } else {
            System.out.println("No hay nada que deshacer en el camión.");
        }
    }

    /**
     * Descripcion: Muestra pendientes en centro, próximo sin sacar, estado del camión y paquete listo para cargar.
     * Precondición: centro y camion no nulos.
     */
    private static void mostrarEstado(
            CentroDistribucionADT<ContenidoPaqueteADT> centro,
            CamionADT<ContenidoPaqueteADT> camion,
            PaqueteADT<ContenidoPaqueteADT> listoParaCamion) {
        System.out.println("--- Estado ---");
        System.out.println("Pendientes en centro: " + centro.cantidadPendiente());
        PaqueteADT<ContenidoPaqueteADT> proxCentro = centro.verSiguienteParaProcesar();
        if (proxCentro != null) {
            System.out.println("Próximo en centro (sin sacar): " + proxCentro);
        } else {
            System.out.println("Próximo en centro: (ninguno)");
        }
        System.out.println("Paquetes en camión: " + camion.cantidadCargada());
        PaqueteADT<ContenidoPaqueteADT> tope = camion.verProximoADescargar();
        if (tope != null) {
            System.out.println("Tope del camión (sale primero): " + tope);
        } else {
            System.out.println("Camión vacío.");
        }
        if (listoParaCamion != null) {
            System.out.println("Listo para cargar al camión: " + listoParaCamion);
        } else {
            System.out.println("Listo para camión: (ninguno)");
        }
    }

    /**
     * Descripcion: Interpreta sí/no para preguntas (s, si, sí, y, yes en minúsculas).
     * Precondición: No tiene (línea nula se trata como no).
     */
    private static boolean esSi(String linea) {
        if (linea == null) {
            return false;
        }
        String s = linea.trim().toLowerCase();
        return s.equals("s") || s.equals("si") || s.equals("sí") || s.equals("y") || s.equals("yes");
    }
}
