package logistica.tipos;

import java.util.Objects;

/** Contenido de tipo electrónica para {@link logistica.Paquete}. */
public class Electronica implements ContenidoPaquete {

    private final String descripcion;
    private final double valorAsegurado;

    public Electronica(String descripcion, double valorAsegurado) {
        this.descripcion = Objects.requireNonNull(descripcion, "descripcion");
        this.valorAsegurado = valorAsegurado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValorAsegurado() {
        return valorAsegurado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Electronica that = (Electronica) o;
        return Double.compare(that.valorAsegurado, valorAsegurado) == 0
                && descripcion.equals(that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, valorAsegurado);
    }

    @Override
    public String toString() {
        return "Electronica{descripcion='" + descripcion + "', valorAsegurado=" + valorAsegurado + "}";
    }
}
