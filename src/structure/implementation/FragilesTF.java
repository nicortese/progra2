package structure.implementation;

import structure.definition.ContenidoPaqueteADT;

// Esta clase implementa contenido tipo Frágiles (TF).

/** Datos de carga frágil para un PaqueteADT. */
public class FragilesTF implements ContenidoPaqueteADT {

    private final String descripcion;
    private final int nivelCuidado;

    /**
     * Descripcion: Crea contenido frágil.
     * Precondición: parámetro descripcion no nulo.
     */
    public FragilesTF(String descripcion, int nivelCuidado) {
        if (descripcion == null) {
            throw new NullPointerException("descripcion");
        }
        this.descripcion = descripcion;
        this.nivelCuidado = nivelCuidado;
    }

    /** Descripcion: Texto descriptivo. Precondición: No tiene. */
    public String getDescripcion() {
        return descripcion;
    }

    /** Descripcion: Nivel de cuidado (entero). Precondición: No tiene. */
    public int getNivelCuidado() {
        return nivelCuidado;
    }

    /** Descripcion: Igualdad por campos. Precondición: No tiene. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FragilesTF fragiles = (FragilesTF) o;
        return nivelCuidado == fragiles.nivelCuidado && descripcion.equals(fragiles.descripcion);
    }

    /** Descripcion: Hash por campos. Precondición: No tiene. */
    @Override
    public int hashCode() {
        return 31 * descripcion.hashCode() + nivelCuidado;
    }

    /** Descripcion: Texto para consola/log. Precondición: No tiene. */
    @Override
    public String toString() {
        return "Fragiles{descripcion='" + descripcion + "', nivelCuidado=" + nivelCuidado + "}";
    }
}
