package structure.definition;

// Esta interfaz representa el TDA Paquete genérico.

/** Paquete con contenido tipado. @param <T> tipo de contenido */
public interface PaqueteADT<T> {

    /** Descripcion: ID único del paquete. Precondición: No tiene. */
    long getId();

    /** Descripcion: Peso en kg. Precondición: No tiene. */
    double getPeso();

    /** Descripcion: Destino del envío. Precondición: No tiene. */
    String getDestino();

    /** Descripcion: Contenido transportado. Precondición: No tiene. */
    T getContenido();

    /** Descripcion: Si está marcado urgente. Precondición: No tiene. */
    boolean esUrgente();

    /**
     * Descripcion: Si tiene prioridad en el centro (urgente o peso mayor al umbral).
     * Precondición: No tiene.
     */
    boolean esPrioridadSobreEstandar();
}
