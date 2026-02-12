
package udbvirtual;

import java.util.Objects;

public class Alumno {
    private final String carnet;
    private final String nombreCompleto;

    public Alumno(String carnet, String nombreCompleto) {
        this.carnet = carnet;
        this.nombreCompleto = nombreCompleto;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public String toString() {
        return "Carnet: " + carnet + " | Nombre: " + nombreCompleto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno)) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(carnet, alumno.carnet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carnet);
    }
}
