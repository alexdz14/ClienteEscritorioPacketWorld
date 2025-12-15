package clientepacketworld.dto;

import java.util.List;

public class RSDatosCodigoPostal {

    private boolean error;
    private String mensaje;
    private String estado;
    private String municipio;
    private List<RSColonia> colonias;

    public RSDatosCodigoPostal() {
    }

    public RSDatosCodigoPostal(boolean error, String mensaje, String estado, String municipio, List<RSColonia> colonias) {
        this.error = error;
        this.mensaje = mensaje;
        this.estado = estado;
        this.municipio = municipio;
        this.colonias = colonias;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public List<RSColonia> getColonias() {
        return colonias;
    }

    public void setColonias(List<RSColonia> colonias) {
        this.colonias = colonias;
    }
}
