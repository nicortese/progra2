package logistica.tipos;

import java.util.Objects;

/** Contenido frágil para {@link logistica.Paquete}. */
public class Fragiles implements ContenidoPaquete {

    private final String descripcion;
    private final int nivelCuidado;

    public Fragiles(String descripcion, int nivelCuidado) {
        this.descripcion = Objects.requireNonNull(descripcion, "descripcion");
        this.nivelCuidado = nivelCuidado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNivelCuidado() {
        return nivelCuidado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fragiles fragiles = (Fragiles) o;
        return nivelCuidado == fragiles.nivelCuidado && descripcion.equals(fragiles.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, nivelCuidado);
    }

    @Override
    public String toString() {
        return "Fragiles{descripcion='" + descripcion + "', nivelCuidado=" + nivelCuidado + "}";
    }
}
