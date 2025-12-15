package clientepacketworld.dto;

import clientepacketworld.pojo.Colaborador;

public class RespuestaLogin {

    private boolean error;
    private String mensaje;
    private Colaborador colaborador;

    public RespuestaLogin() {
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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
