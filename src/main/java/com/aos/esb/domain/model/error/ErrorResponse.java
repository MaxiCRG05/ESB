package com.aos.esb.domain.model.error;

import java.time.LocalDateTime;

public class ErrorResponse
{
    private String codigo;
    private String mensaje;
    private String timestamp;
    private String ruta;

    // Constructor vacío
    public ErrorResponse() { }

    public ErrorResponse(String codigo, String mensaje, String ruta)
    {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now().toString();
        this.ruta = ruta;
    }

    // Getters y Setters
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

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getRuta()
    {
        return ruta;
    }

    public void setRuta(String ruta)
    {
        this.ruta = ruta;
    }

    @Override
    public String toString()
    {
        return "ErrorResponse{" +
                "codigo='" + codigo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", ruta='" + ruta + '\'' +
                '}';
    }
}