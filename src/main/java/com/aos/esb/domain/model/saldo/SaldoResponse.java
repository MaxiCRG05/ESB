package com.aos.esb.domain.model.saldo;

import java.math.BigDecimal;

public class SaldoResponse
{
    private Long cuentaId;
    private BigDecimal saldo;
    private String moneda;

    // Constructor vacío
    public SaldoResponse() { }

    public SaldoResponse(Long cuentaId, BigDecimal saldo, String moneda)
    {
        this.cuentaId = cuentaId;
        this.saldo = saldo;
        this.moneda = moneda;
    }

    // Getters y Setters
    public Long getCuentaId()
    {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId)
    {
        this.cuentaId = cuentaId;
    }

    public BigDecimal getSaldo()
    {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo)
    {
        this.saldo = saldo;
    }

    public String getMoneda()
    {
        return moneda;
    }

    public void setMoneda(String moneda)
    {
        this.moneda = moneda;
    }

    @Override
    public String toString()
    {
        return "SaldoResponse{" +
                "cuentaId=" + cuentaId +
                ", saldo=" + saldo +
                ", moneda='" + moneda + '\'' +
                '}';
    }
}