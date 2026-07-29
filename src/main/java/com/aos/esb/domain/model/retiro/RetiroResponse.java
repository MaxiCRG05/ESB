package com.aos.esb.domain.model.retiro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetiroResponse
{
    private Long transaccionId;
    private Long cuentaId;
    private BigDecimal montoCantidad;
    private String montoMoneda;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private String tipoRetiro;

    public RetiroResponse() { }

    public RetiroResponse(Long transaccionId, Long cuentaId, BigDecimal montoCantidad,
                          String montoMoneda, LocalDateTime fecha, String tipoRetiro)
    {
        this.transaccionId = transaccionId;
        this.cuentaId = cuentaId;
        this.montoCantidad = montoCantidad;
        this.montoMoneda = montoMoneda;
        this.fecha = fecha;
        this.tipoRetiro = tipoRetiro;
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

    public String getTipoRetiro()
    {
        return tipoRetiro;
    }

    public void setTipoRetiro(String tipoRetiro)
    {
        this.tipoRetiro = tipoRetiro;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetiroResponse that = (RetiroResponse) o;
        return Objects.equals(transaccionId, that.transaccionId) &&
                Objects.equals(cuentaId, that.cuentaId) &&
                Objects.equals(montoCantidad, that.montoCantidad) &&
                Objects.equals(montoMoneda, that.montoMoneda) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(tipoRetiro, that.tipoRetiro);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(transaccionId, cuentaId, montoCantidad, montoMoneda, fecha, tipoRetiro);
    }

    @Override
    public String toString()
    {
        return "RetiroResponse{" +
                "transaccionId=" + transaccionId +
                ", cuentaId=" + cuentaId +
                ", montoCantidad=" + montoCantidad +
                ", montoMoneda='" + montoMoneda + '\'' +
                ", fecha=" + fecha +
                ", tipoRetiro='" + tipoRetiro + '\'' +
                '}';
    }
}