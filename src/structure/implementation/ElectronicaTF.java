package structure.implementation;

import structure.definition.ContenidoPaqueteADT;

// Esta clase implementa contenido tipo Electrónica (TF).

/** Datos de carga electrónica para un PaqueteADT. */
public class ElectronicaTF implements ContenidoPaqueteADT {

    private final String descripcion;
    private final double valorAsegurado;

    /**
     * Descripcion: Crea contenido electrónica.
     * Precondición: parámetro descripcion no nulo.
     */
    public ElectronicaTF(String descripcion, double valorAsegurado) {
        if (descripcion == null) {
            throw new NullPointerException("descripcion");
        }
        this.descripcion = descripcion;
        this.valorAsegurado = valorAsegurado;
    }

    /** Descripcion: Texto descriptivo. Precondición: No tiene. */
    public String getDescripcion() {
        return descripcion;
    }

    /** Descripcion: Valor asegurado. Precondición: No tiene. */
    public double getValorAsegurado() {
        return valorAsegurado;
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
        ElectronicaTF that = (ElectronicaTF) o;
        return Double.compare(that.valorAsegurado, valorAsegurado) == 0
                && descripcion.equals(that.descripcion);
    }

    /** Descripcion: Hash por campos. Precondición: No tiene. */
    @Override
    public int hashCode() {
        int h = descripcion.hashCode();
        long bits = Double.doubleToLongBits(valorAsegurado);
        return 31 * h + (int) (bits ^ (bits >>> 32));
    }

    /** Descripcion: Texto para consola/log. Precondición: No tiene. */
    @Override
    public String toString() {
        return "Electronica{descripcion='" + descripcion + "', valorAsegurado=" + valorAsegurado + "}";
    }
}
