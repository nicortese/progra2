package structure.implementation;

import java.util.ArrayDeque;
import java.util.Deque;

import structure.definition.CamionADT;
import structure.definition.PaqueteADT;

// Esta clase implementa el TDA Camión (TF).

/** Pila con ArrayDeque (LIFO). */
public class CamionTF<T> implements CamionADT<T> {

    private final Deque<PaqueteADT<T>> pila;

    /** Descripcion: Camión vacío. Precondición: No tiene. */
    public CamionTF() {
        this.pila = new ArrayDeque<>();
    }

    /**
     * Descripcion: Apila un paquete (implementación con push sobre la Deque).
     * Precondición: parámetro paquete no nulo; si es nulo lanza NullPointerException.
     */
    @Override
    public void cargar(PaqueteADT<T> paquete) {
        if (paquete == null) {
            throw new NullPointerException("paquete");
        }
        pila.push(paquete);
    }

    /** Descripcion: Descarga el tope (LIFO); nulo si vacío. Precondición: No tiene. */
    @Override
    public PaqueteADT<T> descargar() {
        return quitarTope();
    }

    /** Descripcion: Deshace la última carga (O(1)); nulo si vacío. Precondición: No tiene. */
    @Override
    public PaqueteADT<T> deshacerUltimaCarga() {
        return quitarTope();
    }

    /**
     * Descripcion: Saca el tope de la pila o nulo si está vacía.
     * Precondición: No tiene.
     */
    private PaqueteADT<T> quitarTope() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.pop();
    }

    /** Descripcion: Cantidad cargada. Precondición: No tiene. */
    @Override
    public int cantidadCargada() {
        return pila.size();
    }

    /** Descripcion: Si no hay paquetes. Precondición: No tiene. */
    @Override
    public boolean estaVacio() {
        return pila.isEmpty();
    }

    /** Descripcion: Ve el tope sin sacar; nulo si vacío. Precondición: No tiene. */
    @Override
    public PaqueteADT<T> verProximoADescargar() {
        return pila.isEmpty() ? null : pila.peek();
    }
}
