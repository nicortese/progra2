package structure.implementation;

import java.util.Comparator;
import java.util.PriorityQueue;

import structure.definition.CentroDistribucionADT;
import structure.definition.PaqueteADT;

// Esta clase implementa el TDA Centro de distribución (TF).

/** Cola de prioridad (PriorityQueue) + orden de llegada por centro. */
public class CentroDistribucionTF<T> implements CentroDistribucionADT<T> {

    /** Par paquete + número de llegada para desempate FIFO. */
    private static class Entrada<T> {
        final PaqueteADT<T> paquete;
        final long ordenLlegada;

        Entrada(PaqueteADT<T> paquete, long ordenLlegada) {
            this.paquete = paquete;
            this.ordenLlegada = ordenLlegada;
        }
    }

    /** Orden de llegada dentro de este centro (un solo hilo). */
    private long secuenciaLlegada;
    private final PriorityQueue<Entrada<T>> cola;

    /** Descripcion: Centro sin paquetes. Precondición: No tiene. */
    public CentroDistribucionTF() {
        Comparator<Entrada<T>> cmp = Comparator
                .comparing((Entrada<T> e) -> e.paquete.esPrioridadSobreEstandar() ? 0 : 1)
                .thenComparingLong(e -> e.ordenLlegada);
        this.cola = new PriorityQueue<>(cmp);
    }

    /**
     * Descripcion: Encola un paquete (implementación con PriorityQueue y orden de llegada).
     * Precondición: parámetro paquete no nulo; si es nulo lanza NullPointerException.
     */
    @Override
    public void ingresar(PaqueteADT<T> paquete) {
        if (paquete == null) {
            throw new NullPointerException("paquete");
        }
        long orden = secuenciaLlegada++;
        cola.add(new Entrada<>(paquete, orden));
    }

    /** Descripcion: Saca el siguiente a procesar; nulo si no hay. Precondición: No tiene. */
    @Override
    public PaqueteADT<T> retirarSiguienteParaProcesar() {
        Entrada<T> e = cola.poll();
        return e == null ? null : e.paquete;
    }

    /** Descripcion: Mira el siguiente sin sacar; nulo si no hay. Precondición: No tiene. */
    @Override
    public PaqueteADT<T> verSiguienteParaProcesar() {
        Entrada<T> e = cola.peek();
        return e == null ? null : e.paquete;
    }

    /** Descripcion: Cantidad pendiente. Precondición: No tiene. */
    @Override
    public int cantidadPendiente() {
        return cola.size();
    }

    /** Descripcion: Si hay al menos uno pendiente. Precondición: No tiene. */
    @Override
    public boolean hayPendientes() {
        return !cola.isEmpty();
    }
}
