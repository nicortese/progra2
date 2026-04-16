package structure.definition;

// Esta interfaz representa el TDA Camión (carga en pila / LIFO).

/** Camión LIFO; si no hay paquete, los métodos correspondientes devuelven nulo. @param <T> contenido */
public interface CamionADT<T> {

    /** Descripcion: Apila un paquete. Precondición: parámetro paquete no nulo. */
    void cargar(PaqueteADT<T> paquete);

    /** Descripcion: Descarga el tope (LIFO); nulo si vacío. Precondición: No tiene. */
    PaqueteADT<T> descargar();

    /** Descripcion: Deshace la última carga (O(1)); nulo si vacío. Precondición: No tiene. */
    PaqueteADT<T> deshacerUltimaCarga();

    /** Descripcion: Cantidad cargada. Precondición: No tiene. */
    int cantidadCargada();

    /** Descripcion: Si no hay paquetes. Precondición: No tiene. */
    boolean estaVacio();

    /** Descripcion: Ve el tope sin sacar; nulo si vacío. Precondición: No tiene. */
    PaqueteADT<T> verProximoADescargar();
}
