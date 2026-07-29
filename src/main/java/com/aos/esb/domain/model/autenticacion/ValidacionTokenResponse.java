package com.aos.esb.domain.model.autenticacion;

public class ValidacionTokenResponse
{
    private boolean valido;
    private int usuarioId;
    private String correo;
    private String mensaje;

    public ValidacionTokenResponse() {}

    public ValidacionTokenResponse(boolean valido, int usuarioId, String correo, String mensaje)
    {
        this.valido = valido;
        this.usuarioId = usuarioId;
        this.correo = correo;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public boolean isValido()
    {
        return valido;
    }

    public void setValido(boolean valido)
    {
        this.valido = valido;
    }

    public int getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }
}