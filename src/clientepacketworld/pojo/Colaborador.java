package clientepacketworld.pojo;

public class Colaborador {

    private Integer idColaborador;

    private String noPersonal;

    private String contrasenia;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String curp;

    private String correo;

    private String foto; // Base64

    private String rol;

    private String numLicencia;

    private Integer idSucursal;

    public Colaborador() {

    }

    public Colaborador(Integer idColaborador, String noPersonal, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String curp, String correo, String foto, String rol, String numLicencia, Integer idSucursal) {

        this.idColaborador = idColaborador;

        this.noPersonal = noPersonal;

        this.contrasenia = contrasenia;

        this.nombre = nombre;

        this.apellidoPaterno = apellidoPaterno;

        this.apellidoMaterno = apellidoMaterno;

        this.curp = curp;

        this.correo = correo;

        this.foto = foto;

        this.rol = rol;

        this.numLicencia = numLicencia;

        this.idSucursal = idSucursal;

    }

    // Getters y Setters
    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNumLicencia() {
        return numLicencia;
    }

    public void setNumLicencia(String numLicencia) {
        this.numLicencia = numLicencia;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override

    public String toString() {

        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;

    }

}
