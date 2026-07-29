package com.aos.esb.domain.model.health;

public class HealthResponse
{
    private String status;
    private String service;
    private String version;

    // Constructor vacío
    public HealthResponse() { }

    public HealthResponse(String status, String service, String version)
    {
        this.status = status;
        this.service = service;
        this.version = version;
    }

    // Getters y Setters
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "HealthResponse{" +
                "status='" + status + '\'' +
                ", service='" + service + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}