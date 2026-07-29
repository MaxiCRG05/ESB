package com.aos.esb.domain.model.consulta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaResponse
{
    private boolean exito;
    private String codigo;
    private String mensaje;
    private Map<String, Object> datos;

    public ConsultaResponse() { }

    public ConsultaResponse(boolean exito, String codigo, String mensaje, Map<String, Object> datos)
    {
        this.exito = exito;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public boolean isExito()
    {
        return exito;
    }

    public void setExito(boolean exito)
    {
        this.exito = exito;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public Map<String, Object> getDatos()
    {
        return datos;
    }

    public void setDatos(Map<String, Object> datos)
    {
        this.datos = datos;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaResponse that = (ConsultaResponse) o;
        return exito == that.exito &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(mensaje, that.mensaje) &&
                Objects.equals(datos, that.datos);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(exito, codigo, mensaje, datos);
    }

    @Override
    public String toString()
    {
        return "ConsultaResponse{" +
                "exito=" + exito +
                ", codigo='" + codigo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", datos=" + datos +
                '}';
    }
}