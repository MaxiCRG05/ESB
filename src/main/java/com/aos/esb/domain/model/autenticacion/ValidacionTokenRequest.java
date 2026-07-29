package com.aos.esb.domain.model.autenticacion;

public class ValidacionTokenRequest
{
    private String token;

    public ValidacionTokenRequest() {}

    public ValidacionTokenRequest(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}