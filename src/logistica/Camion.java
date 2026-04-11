package logistica;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

/**
 * Gestión de carga de un camión: el último paquete cargado es el primero en descargarse (LIFO).
 * <p>
 * Implementación basada en una pila: {@link #cargar}, {@link #descargar} y
 * {@link #deshacerUltimaCarga} son O(1) amortizado.
 *
 * @param <T> tipo de contenido de los paquetes (alineado con {@link Paquete})
 */
public class Camion<T> {

    private final Deque<Paquete<T>> pila;

    public Camion() {
        this.pila = new ArrayDeque<>();
    }

    /**
     * Apila un paquete en el camión. Queda arriba de la pila y será el próximo en salir.
     */
    public void cargar(Paquete<T> paquete) {
        Objects.requireNonNull(paquete, "paquete");
        pila.push(paquete);
    }

    /**
     * Descarga un paquete en el punto de distribución (orden LIFO respecto de la carga).
     *
     * @return el paquete descargado, o vacío si no hay carga
     */
    public Optional<Paquete<T>> descargar() {
        return quitarTope();
    }

    /**
     * Deshace la última carga (p. ej. error de destino). Quita el paquete más recientemente cargado.
     *
     * @return el paquete retirado, o vacío si no había carga
     */
    public Optional<Paquete<T>> deshacerUltimaCarga() {
        return quitarTope();
    }

    private Optional<Paquete<T>> quitarTope() {
        if (pila.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(pila.pop());
    }

    /** Cantidad de paquetes a bordo. O(1). */
    public int cantidadCargada() {
        return pila.size();
    }

    /** Indica si no hay paquetes. O(1). */
    public boolean estaVacio() {
        return pila.isEmpty();
    }

    /**
     * Consulta el próximo paquete a descargar o a deshacer, sin modificar la pila. O(1).
     */
    public Optional<Paquete<T>> verProximoADescargar() {
        return pila.isEmpty() ? Optional.empty() : Optional.of(pila.peek());
    }
}
