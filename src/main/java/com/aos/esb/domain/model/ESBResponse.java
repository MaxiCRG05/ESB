package com.aos.esb.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ESBResponse
{
    private Header header;
    private Body body;

    public ESBResponse() { }

    public ESBResponse(Header header, Body body)
    {
        this.header = header;
        this.body = body;
    }

    public Header getHeader()
    {
        return header;
    }

    public void setHeader(Header header)
    {
        this.header = header;
    }

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body
    {
        private String codigoEstatus;
        private boolean exito;
        private String mensaje;
        private Object datos;

        public Body() { }

        public Body(String codigoEstatus, boolean exito, String mensaje, Object datos)
        {
            this.codigoEstatus = codigoEstatus;
            this.exito = exito;
            this.mensaje = mensaje;
            this.datos = datos;
        }

        public String getCodigoEstatus()
        {
            return codigoEstatus;
        }

        public void setCodigoEstatus(String codigoEstatus)
        {
            this.codigoEstatus = codigoEstatus;
        }

        public boolean isExito()
        {
            return exito;
        }

        public void setExito(boolean exito)
        {
            this.exito = exito;
        }

        public String getMensaje()
        {
            return mensaje;
        }

        public void setMensaje(String mensaje)
        {
            this.mensaje = mensaje;
        }

        public Object getDatos()
        {
            return datos;
        }

        public void setDatos(Object datos)
        {
            this.datos = datos;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Body body = (Body) o;
            return exito == body.exito &&
                    Objects.equals(codigoEstatus, body.codigoEstatus) &&
                    Objects.equals(mensaje, body.mensaje) &&
                    Objects.equals(datos, body.datos);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(codigoEstatus, exito, mensaje, datos);
        }

        @Override
        public String toString()
        {
            return "Body{" +
                    "codigoEstatus='" + codigoEstatus + '\'' +
                    ", exito=" + exito +
                    ", mensaje='" + mensaje + '\'' +
                    ", datos=" + datos +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESBResponse that = (ESBResponse) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(header, body);
    }

    @Override
    public String toString()
    {
        return "ESBResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}