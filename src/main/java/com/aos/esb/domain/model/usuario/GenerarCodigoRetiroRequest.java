package com.aos.esb.domain.model.usuario;

import java.math.BigDecimal;

public class GenerarCodigoRetiroRequest
{
    private BigDecimal monto;
    private String moneda = "MXN";
    private Integer cuentaId;

    public GenerarCodigoRetiroRequest() { }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public Integer getCuentaId() { return cuentaId; }
    public void setCuentaId(Integer cuentaId) { this.cuentaId = cuentaId; }
}