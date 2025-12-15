package clientepacketworld.dto;

public class RSColonia {

    private String nombre;

    public RSColonia() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
