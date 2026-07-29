package com.aos.esb.domain.model.deposito;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositoResponse
{
    private Long transaccionId;
    private Long cuentaId;
    private BigDecimal montoCantidad;
    private String montoMoneda;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private String metodo;
    private String referencia;

    public DepositoResponse() { }

    public DepositoResponse(Long transaccionId, Long cuentaId, BigDecimal montoCantidad,
                            String montoMoneda, LocalDateTime fecha, String metodo, String referencia)
    {
        this.transaccionId = transaccionId;
        this.cuentaId = cuentaId;
        this.montoCantidad = montoCantidad;
        this.montoMoneda = montoMoneda;
        this.fecha = fecha;
        this.metodo = metodo;
        this.referencia = referencia;
    }

    public Long getTransaccionId()
    {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId)
    {
        this.transaccionId = transaccionId;
    }

    public Long getCuentaId()
    {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId)
    {
        this.cuentaId = cuentaId;
    }

    public BigDecimal getMontoCantidad()
    {
        return montoCantidad;
    }

    public void setMontoCantidad(BigDecimal montoCantidad)
    {
        this.montoCantidad = montoCantidad;
    }

    public String getMontoMoneda()
    {
        return montoMoneda;
    }

    public void setMontoMoneda(String montoMoneda)
    {
        this.montoMoneda = montoMoneda;
    }

    public LocalDateTime getFecha()
    {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha)
    {
        this.fecha = fecha;
    }

    public String getMetodo()
    {
        return metodo;
    }

    public void setMetodo(String metodo)
    {
        this.metodo = metodo;
    }

    public String getReferencia()
    {
        return referencia;
    }

    public void setReferencia(String referencia)
    {
        this.referencia = referencia;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositoResponse that = (DepositoResponse) o;
        return Objects.equals(transaccionId, that.transaccionId) &&
                Objects.equals(cuentaId, that.cuentaId) &&
                Objects.equals(montoCantidad, that.montoCantidad) &&
                Objects.equals(montoMoneda, that.montoMoneda) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(metodo, that.metodo) &&
                Objects.equals(referencia, that.referencia);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(transaccionId, cuentaId, montoCantidad, montoMoneda, fecha, metodo, referencia);
    }

    @Override
    public String toString()
    {
        return "DepositoResponse{" +
                "transaccionId=" + transaccionId +
                ", cuentaId=" + cuentaId +
                ", montoCantidad=" + montoCantidad +
                ", montoMoneda='" + montoMoneda + '\'' +
                ", fecha=" + fecha +
                ", metodo='" + metodo + '\'' +
                ", referencia='" + referencia + '\'' +
                '}';
    }
}