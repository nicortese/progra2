package logistica;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Cola de preparación en el centro de distribución: los paquetes con prioridad
 * ({@link Paquete#esPrioridadSobreEstandar()}) salen antes que los estándar;
 * dentro de cada grupo se respeta el orden de llegada (FIFO).
 * <p>
 * {@link #ingresar} y {@link #retirarSiguienteParaProcesar} son O(log n) con la
 * implementación basada en {@link PriorityQueue}.
 *
 * @param <T> tipo de contenido de los paquetes
 */
public class CentroDistribucion<T> {

    private static class Entrada<T> {
        final Paquete<T> paquete;
        final long ordenLlegada;

        Entrada(Paquete<T> paquete, long ordenLlegada) {
            this.paquete = paquete;
            this.ordenLlegada = ordenLlegada;
        }
    }

    private final AtomicLong secuenciaLlegada = new AtomicLong(0L);
    private final PriorityQueue<Entrada<T>> cola;

    public CentroDistribucion() {
        Comparator<Entrada<T>> cmp = Comparator
                .comparing((Entrada<T> e) -> e.paquete.esPrioridadSobreEstandar() ? 0 : 1)
                .thenComparingLong(e -> e.ordenLlegada);
        this.cola = new PriorityQueue<>(cmp);
    }

    /** Registra un paquete que llegó al centro y queda listo para su turno de procesamiento. */
    public void ingresar(Paquete<T> paquete) {
        Objects.requireNonNull(paquete, "paquete");
        long orden = secuenciaLlegada.getAndIncrement();
        cola.add(new Entrada<>(paquete, orden));
    }

    /** Obtiene el siguiente paquete a procesar según prioridad y orden de llegada. */
    public Optional<Paquete<T>> retirarSiguienteParaProcesar() {
        Entrada<T> e = cola.poll();
        return e == null ? Optional.empty() : Optional.of(e.paquete);
    }

    /** Mira el próximo a procesar sin sacarlo de la cola. */
    public Optional<Paquete<T>> verSiguienteParaProcesar() {
        Entrada<T> e = cola.peek();
        return e == null ? Optional.empty() : Optional.of(e.paquete);
    }

    public int cantidadPendiente() {
        return cola.size();
    }

    public boolean hayPendientes() {
        return !cola.isEmpty();
    }
}
