package structure.definition;

// Esta interfaz representa el TDA Centro de distribución.

/** Cola con prioridad + FIFO por nivel; nulo cuando no hay paquete. @param <T> contenido */
public interface CentroDistribucionADT<T> {

    /** Descripcion: Encola un paquete. Precondición: parámetro paquete no nulo. */
    void ingresar(PaqueteADT<T> paquete);

    /** Descripcion: Saca el siguiente a procesar; nulo si no hay. Precondición: No tiene. */
    PaqueteADT<T> retirarSiguienteParaProcesar();

    /** Descripcion: Mira el siguiente sin sacar; nulo si no hay. Precondición: No tiene. */
    PaqueteADT<T> verSiguienteParaProcesar();

    /** Descripcion: Cantidad pendiente. Precondición: No tiene. */
    int cantidadPendiente();

    /** Descripcion: Si hay al menos uno pendiente. Precondición: No tiene. */
    boolean hayPendientes();
}
