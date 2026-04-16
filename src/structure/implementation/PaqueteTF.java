package structure.implementation;

import structure.definition.PaqueteADT;

// Esta clase implementa el TDA Paquete (TF).

/** Implementación de PaqueteADT con ID autonumérico y prioridad del centro (50 kg). */
public class PaqueteTF<T> implements PaqueteADT<T> {

    /** Contador de IDs (programa de consola, un solo hilo). */
    private static long siguienteId = 1L;

    /** Umbral de peso (kg) a partir del cual el paquete es prioritario en el centro. */
    public static final double PESO_KG_PRIORITARIO = 50.0;

    private final long id;
    private final double peso;
    private final String destino;
    private final T contenido;
    private final boolean urgente;

    /**
     * Descripcion: Crea paquete no urgente. Asigna ID único.
     * Precondición: Mismas validaciones que el constructor completo; peso mayor a cero, destino no vacío,
     * contenido no nulo.
     */
    public PaqueteTF(double peso, String destino, T contenido) {
        this(peso, destino, contenido, false);
    }

    /**
     * Descripcion: Crea paquete con flag urgente y asigna ID único.
     * Precondición: peso mayor a cero; destino no nulo ni vacío; contenido no nulo.
     */
    public PaqueteTF(double peso, String destino, T contenido, boolean urgente) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser positivo");
        }
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino no puede ser nulo ni vacío");
        }
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }
        this.id = siguienteId++;
        this.peso = peso;
        this.destino = destino.trim();
        this.contenido = contenido;
        this.urgente = urgente;
    }

    /** Descripcion: ID único. Precondición: No tiene. */
    @Override
    public long getId() {
        return id;
    }

    /** Descripcion: Peso en kg. Precondición: No tiene. */
    @Override
    public double getPeso() {
        return peso;
    }

    /** Descripcion: Destino. Precondición: No tiene. */
    @Override
    public String getDestino() {
        return destino;
    }

    /** Descripcion: Contenido. Precondición: No tiene. */
    @Override
    public T getContenido() {
        return contenido;
    }

    /** Descripcion: Marcado urgente. Precondición: No tiene. */
    @Override
    public boolean esUrgente() {
        return urgente;
    }

    /** Descripcion: Urgente o peso mayor a PESO_KG_PRIORITARIO. Precondición: No tiene. */
    @Override
    public boolean esPrioridadSobreEstandar() {
        return urgente || peso > PESO_KG_PRIORITARIO;
    }

    /** Descripcion: Igualdad por id. Precondición: No tiene. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaqueteTF<?> paquete = (PaqueteTF<?>) o;
        return id == paquete.id;
    }

    /** Descripcion: Hash según id. Precondición: No tiene. */
    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    /** Descripcion: Representación texto del paquete. Precondición: No tiene. */
    @Override
    public String toString() {
        return "Paquete{id=" + id + ", peso=" + peso + ", destino='" + destino + "', urgente=" + urgente
                + ", contenido=" + contenido + "}";
    }
}
