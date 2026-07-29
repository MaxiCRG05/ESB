package com.aos.esb.domain.model.transferencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferenciaResponse
{
    private Long transferenciaId;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private Monto monto;
    private String concepto;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    private OffsetDateTime fecha;
    private Monto saldoOrigenAnterior;
    private Monto saldoOrigenNuevo;
    private Monto saldoDestinoAnterior;
    private Monto saldoDestinoNuevo;

    public TransferenciaResponse() { }

    public TransferenciaResponse(Long transferenciaId, Long cuentaOrigenId, Long cuentaDestinoId,
                                 Monto monto, String concepto, OffsetDateTime fecha,
                                 Monto saldoOrigenAnterior, Monto saldoOrigenNuevo,
                                 Monto saldoDestinoAnterior, Monto saldoDestinoNuevo)
    {
        this.transferenciaId = transferenciaId;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.monto = monto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.saldoOrigenAnterior = saldoOrigenAnterior;
        this.saldoOrigenNuevo = saldoOrigenNuevo;
        this.saldoDestinoAnterior = saldoDestinoAnterior;
        this.saldoDestinoNuevo = saldoDestinoNuevo;
    }

    public Long getTransferenciaId()
    {
        return transferenciaId;
    }

    public void setTransferenciaId(Long transferenciaId)
    {
        this.transferenciaId = transferenciaId;
    }

    public Long getCuentaOrigenId()
    {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Long cuentaOrigenId)
    {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public Long getCuentaDestinoId()
    {
        return cuentaDestinoId;
    }

    public void setCuentaDestinoId(Long cuentaDestinoId)
    {
        this.cuentaDestinoId = cuentaDestinoId;
    }

    public Monto getMonto()
    {
        return monto;
    }

    public void setMonto(Monto monto)
    {
        this.monto = monto;
    }

    public String getConcepto()
    {
        return concepto;
    }

    public void setConcepto(String concepto)
    {
        this.concepto = concepto;
    }

    public OffsetDateTime getFecha()
    {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha)
    {
        this.fecha = fecha;
    }

    public Monto getSaldoOrigenAnterior()
    {
        return saldoOrigenAnterior;
    }

    public void setSaldoOrigenAnterior(Monto saldoOrigenAnterior)
    {
        this.saldoOrigenAnterior = saldoOrigenAnterior;
    }

    public Monto getSaldoOrigenNuevo()
    {
        return saldoOrigenNuevo;
    }

    public void setSaldoOrigenNuevo(Monto saldoOrigenNuevo)
    {
        this.saldoOrigenNuevo = saldoOrigenNuevo;
    }

    public Monto getSaldoDestinoAnterior()
    {
        return saldoDestinoAnterior;
    }

    public void setSaldoDestinoAnterior(Monto saldoDestinoAnterior)
    {
        this.saldoDestinoAnterior = saldoDestinoAnterior;
    }

    public Monto getSaldoDestinoNuevo()
    {
        return saldoDestinoNuevo;
    }

    public void setSaldoDestinoNuevo(Monto saldoDestinoNuevo)
    {
        this.saldoDestinoNuevo = saldoDestinoNuevo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Monto
    {
        private java.math.BigDecimal cantidad;
        private String moneda;

        public Monto() { }

        public Monto(java.math.BigDecimal cantidad, String moneda)
        {
            this.cantidad = cantidad;
            this.moneda = moneda;
        }

        public java.math.BigDecimal getCantidad()
        {
            return cantidad;
        }

        public void setCantidad(java.math.BigDecimal cantidad)
        {
            this.cantidad = cantidad;
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
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Monto monto = (Monto) o;
            return Objects.equals(cantidad, monto.cantidad) &&
                    Objects.equals(moneda, monto.moneda);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(cantidad, moneda);
        }

        @Override
        public String toString()
        {
            return "Monto{" +
                    "cantidad=" + cantidad +
                    ", moneda='" + moneda + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferenciaResponse that = (TransferenciaResponse) o;
        return Objects.equals(transferenciaId, that.transferenciaId) &&
                Objects.equals(cuentaOrigenId, that.cuentaOrigenId) &&
                Objects.equals(cuentaDestinoId, that.cuentaDestinoId) &&
                Objects.equals(monto, that.monto) &&
                Objects.equals(concepto, that.concepto) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(saldoOrigenAnterior, that.saldoOrigenAnterior) &&
                Objects.equals(saldoOrigenNuevo, that.saldoOrigenNuevo) &&
                Objects.equals(saldoDestinoAnterior, that.saldoDestinoAnterior) &&
                Objects.equals(saldoDestinoNuevo, that.saldoDestinoNuevo);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(transferenciaId, cuentaOrigenId, cuentaDestinoId, monto,
                concepto, fecha, saldoOrigenAnterior, saldoOrigenNuevo,
                saldoDestinoAnterior, saldoDestinoNuevo);
    }

    @Override
    public String toString()
    {
        return "TransferenciaResponse{" +
                "transferenciaId=" + transferenciaId +
                ", cuentaOrigenId=" + cuentaOrigenId +
                ", cuentaDestinoId=" + cuentaDestinoId +
                ", monto=" + monto +
                ", concepto='" + concepto + '\'' +
                ", fecha=" + fecha +
                ", saldoOrigenAnterior=" + saldoOrigenAnterior +
                ", saldoOrigenNuevo=" + saldoOrigenNuevo +
                ", saldoDestinoAnterior=" + saldoDestinoAnterior +
                ", saldoDestinoNuevo=" + saldoDestinoNuevo +
                '}';
    }
}