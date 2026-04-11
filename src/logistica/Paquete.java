package logistica;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TDA genérico que representa un paquete con contenido tipado {@code T}.
 * Cada instancia tiene un identificador único asignado al crearse.
 *
 * @param <T> tipo del contenido transportado (p. ej. implementaciones de {@link logistica.tipos.ContenidoPaquete})
 */
public class Paquete<T> {

    private static final AtomicLong SIGUIENTE_ID = new AtomicLong(1L);

    /** Umbral de peso (kg) a partir del cual el paquete se trata como prioritario en el centro. */
    public static final double PESO_KG_PRIORITARIO = 50.0;

    private final long id;
    private final double peso;
    private final String destino;
    private final T contenido;
    private final boolean urgente;

    /**
     * Crea un paquete estándar (no urgente) con ID único automático.
     */
    public Paquete(double peso, String destino, T contenido) {
        this(peso, destino, contenido, false);
    }

    /**
     * Crea un paquete con ID único automático.
     *
     * @param urgente si está marcado como urgente para el centro de distribución
     */
    public Paquete(double peso, String destino, T contenido, boolean urgente) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser positivo");
        }
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino no puede ser nulo ni vacío");
        }
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }
        this.id = SIGUIENTE_ID.getAndIncrement();
        this.peso = peso;
        this.destino = destino.trim();
        this.contenido = contenido;
        this.urgente = urgente;
    }

    public long getId() {
        return id;
    }

    public double getPeso() {
        return peso;
    }

    public String getDestino() {
        return destino;
    }

    public T getContenido() {
        return contenido;
    }

    /** Indica si el envío fue marcado explícitamente como urgente. */
    public boolean esUrgente() {
        return urgente;
    }

    /**
     * Regla del centro: urgente o peso estrictamente mayor a {@value Paquete#PESO_KG_PRIORITARIO} kg
     * implica procesamiento antes que envíos estándar.
     */
    public boolean esPrioridadSobreEstandar() {
        return urgente || peso > PESO_KG_PRIORITARIO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paquete<?> paquete = (Paquete<?>) o;
        return id == paquete.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Paquete{id=" + id + ", peso=" + peso + ", destino='" + destino + "', urgente=" + urgente
                + ", contenido=" + contenido + "}";
    }
}
