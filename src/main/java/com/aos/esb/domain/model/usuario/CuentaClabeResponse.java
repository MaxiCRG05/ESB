package com.aos.esb.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CuentaClabeResponse
{
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("UsuarioId")
    private Integer usuarioId;
    @JsonProperty("CLABE")
    private String clabe;

    public CuentaClabeResponse() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public String getClabe() { return clabe; }
    public void setClabe(String clabe) { this.clabe = clabe; }
}