package logistica.tipos;

import java.util.Objects;

/** Contenido de tipo alimentos para {@link logistica.Paquete}. */
public class Alimentos implements ContenidoPaquete {

    private final String nombre;
    private final boolean requiereFrio;

    public Alimentos(String nombre, boolean requiereFrio) {
        this.nombre = Objects.requireNonNull(nombre, "nombre");
        this.requiereFrio = requiereFrio;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isRequiereFrio() {
        return requiereFrio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alimentos alimentos = (Alimentos) o;
        return requiereFrio == alimentos.requiereFrio && nombre.equals(alimentos.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, requiereFrio);
    }

    @Override
    public String toString() {
        return "Alimentos{nombre='" + nombre + "', requiereFrio=" + requiereFrio + "}";
    }
}
