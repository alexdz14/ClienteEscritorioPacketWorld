package clientepacketworld.pojo;

import java.util.List;

public class Direccion {

    private String estado;
    private String ciudad;
    private List<String> colonias;

    public Direccion() {
    }

    public Direccion(String estado, String ciudad, List<String> colonias) {
        this.estado = estado;
        this.ciudad = ciudad;
        this.colonias = colonias;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<String> getColonias() {
        return colonias;
    }

    public void setColonias(List<String> colonias) {
        this.colonias = colonias;
    }

    @Override
    public String toString() {
        return ciudad + ", " + estado;
    }
}
