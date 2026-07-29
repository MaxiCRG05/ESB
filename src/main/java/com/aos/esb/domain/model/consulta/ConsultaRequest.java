package com.aos.esb.domain.model.consulta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaRequest
{
    @NotNull(message = "El tipo de consulta es obligatorio")
    private String tipoConsulta;

    private Map<String, Object> parametros;

    public ConsultaRequest() { }

    public ConsultaRequest(String tipoConsulta, Map<String, Object> parametros)
    {
        this.tipoConsulta = tipoConsulta;
        this.parametros = parametros;
    }

    public String getTipoConsulta()
    {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta)
    {
        this.tipoConsulta = tipoConsulta;
    }

    public Map<String, Object> getParametros()
    {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros)
    {
        this.parametros = parametros;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaRequest that = (ConsultaRequest) o;
        return Objects.equals(tipoConsulta, that.tipoConsulta) &&
                Objects.equals(parametros, that.parametros);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tipoConsulta, parametros);
    }

    @Override
    public String toString()
    {
        return "ConsultaRequest{" +
                "tipoConsulta='" + tipoConsulta + '\'' +
                ", parametros=" + parametros +
                '}';
    }
}