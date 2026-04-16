package structure.implementation;

import structure.definition.ContenidoPaqueteADT;

// Esta clase implementa contenido tipo Alimentos (TF).

/** Datos de carga de alimentos para un PaqueteADT. */
public class AlimentosTF implements ContenidoPaqueteADT {

    private final String nombre;
    private final boolean requiereFrio;

    /**
     * Descripcion: Crea contenido alimentos.
     * Precondición: parámetro nombre no nulo.
     */
    public AlimentosTF(String nombre, boolean requiereFrio) {
        if (nombre == null) {
            throw new NullPointerException("nombre");
        }
        this.nombre = nombre;
        this.requiereFrio = requiereFrio;
    }

    /** Descripcion: Nombre del producto. Precondición: No tiene. */
    public String getNombre() {
        return nombre;
    }

    /** Descripcion: Si requiere cadena de frío. Precondición: No tiene. */
    public boolean isRequiereFrio() {
        return requiereFrio;
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
        AlimentosTF alimentos = (AlimentosTF) o;
        return requiereFrio == alimentos.requiereFrio && nombre.equals(alimentos.nombre);
    }

    /** Descripcion: Hash por campos. Precondición: No tiene. */
    @Override
    public int hashCode() {
        return 31 * nombre.hashCode() + (requiereFrio ? 1 : 0);
    }

    /** Descripcion: Texto para consola/log. Precondición: No tiene. */
    @Override
    public String toString() {
        return "Alimentos{nombre='" + nombre + "', requiereFrio=" + requiereFrio + "}";
    }
}
