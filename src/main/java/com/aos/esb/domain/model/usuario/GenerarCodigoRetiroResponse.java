package com.aos.esb.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerarCodigoRetiroResponse
{
    @JsonProperty("exito")
    private Boolean exito;
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("codigo")
    private String codigo;

    public GenerarCodigoRetiroResponse() {}

    public Boolean getExito() { return exito; }
    public void setExito(Boolean exito) { this.exito = exito; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}