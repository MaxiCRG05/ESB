package com.aos.esb.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class UsuarioResponse
{
    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("Nombres")
    private String nombres;

    @JsonProperty("ApellidoPaterno")
    private String apellidoPaterno;

    @JsonProperty("ApellidoMaterno")
    private String apellidoMaterno;

    @JsonProperty("Correo")
    private String correo;

    @JsonProperty("Telefono")
    private String telefono;

    @JsonProperty("Activo")
    private Boolean activo;

    public UsuarioResponse() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}