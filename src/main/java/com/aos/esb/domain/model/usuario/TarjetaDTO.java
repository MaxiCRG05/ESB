package com.aos.esb.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TarjetaDTO
{
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Numero")
    private String numero;
    @JsonProperty("Estado")
    private String estado;
    @JsonProperty("FechaExpiracion")
    private String fechaExpiracion;

    public TarjetaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}