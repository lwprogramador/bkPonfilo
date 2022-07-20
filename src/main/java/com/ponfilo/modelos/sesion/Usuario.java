package com.ponfilo.modelos.sesion;


public class Usuario{
    public String getUsrCodigo() {
        return usrCodigo;
    }

    public void setUsrCodigo(String usrCodigo) {
        this.usrCodigo = usrCodigo;
    }

    public String getUsrUsuario() {
        return usrusuario;
    }

    public void setUsrUsuario(String usrUsuario) {
        this.usrusuario = usrUsuario;
    }

    public int getUsrEstado() {
        return usrestado;
    }

    public void setUsrEstado(int usrEstado) {
        this.usrestado = usrEstado;
    }

    public String getUsrEstadoDet() {
        return usrestadodet;
    }

    public void setUsrEstadoDet(String usrEstadoDet) {
        this.usrestadodet = usrEstadoDet;
    }

    public String getUsrCodigoEntidad() {
        return usrcodigoentidad;
    }

    public void setUsrCodigoEntidad(String usrCodigoEntidad) {
        this.usrcodigoentidad = usrCodigoEntidad;
    }

    public String getUsrNombres() {
        return usrnombres;
    }

    public void setUsrNombres(String usrNombres) {
        this.usrnombres = usrNombres;
    }

    public String getUsrApellidos() {
        return usrapellidos;
    }

    public void setUsrApellidos(String usrApellidos) {
        this.usrapellidos = usrApellidos;
    }

    public String getUsrTpoDocumentoAbr() {
        return usrtpodocumentoabr;
    }

    public void setUsrTpoDocumentoAbr(String usrTpoDocumentoAbr) {
        this.usrtpodocumentoabr = usrTpoDocumentoAbr;
    }

    public String getUsrTpoDocumento() {
        return usrtpodocumento;
    }

    public void setUsrTpoDocumento(String usrTpoDocumento) {
        this.usrtpodocumento = usrTpoDocumento;
    }

    public String getUsrDocumento() {
        return usrdocumento;
    }

    public void setUsrDocumento(String usrDocumento) {
        this.usrdocumento = usrDocumento;
    }
    
    private String usrCodigo;
    private String usrusuario;
    private int usrestado;
    private String usrestadodet;
    private String usrcodigoentidad;
    private String usrnombres;
    private String usrapellidos;
    private String usrtpodocumentoabr;
    private String usrtpodocumento;
    private String usrdocumento;
}
