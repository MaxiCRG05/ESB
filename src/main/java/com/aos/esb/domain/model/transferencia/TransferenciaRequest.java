package com.aos.esb.domain.model.transferencia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferenciaRequest
{
    @Valid
    @NotNull(message = "El monto es obligatorio")
    private Monto monto;

    @NotNull(message = "La CLABE destino es obligatoria")
    private String clabeDestino;

    private String concepto;

    private Long cuentaOrigenId;

    public TransferenciaRequest() { }

    public TransferenciaRequest(Monto monto, String clabeDestino, String concepto, Long cuentaOrigenId)
    {
        this.monto = monto;
        this.clabeDestino = clabeDestino;
        this.concepto = concepto;
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public Monto getMonto()
    {
        return monto;
    }

    public void setMonto(Monto monto)
    {
        this.monto = monto;
    }

    public String getClabeDestino()
    {
        return clabeDestino;
    }

    public void setClabeDestino(String clabeDestino)
    {
        this.clabeDestino = clabeDestino;
    }

    public String getConcepto()
    {
        return concepto;
    }

    public void setConcepto(String concepto)
    {
        this.concepto = concepto;
    }

    public Long getCuentaOrigenId()
    {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Long cuentaOrigenId)
    {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Monto
    {
        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a cero")
        private BigDecimal cantidad;

        @NotNull(message = "La moneda es obligatoria")
        private String moneda;

        public Monto() { }

        public Monto(BigDecimal cantidad, String moneda)
        {
            this.cantidad = cantidad;
            this.moneda = moneda;
        }

        public BigDecimal getCantidad()
        {
            return cantidad;
        }

        public void setCantidad(BigDecimal cantidad)
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
        TransferenciaRequest that = (TransferenciaRequest) o;
        return Objects.equals(monto, that.monto) &&
                Objects.equals(clabeDestino, that.clabeDestino) &&
                Objects.equals(concepto, that.concepto) &&
                Objects.equals(cuentaOrigenId, that.cuentaOrigenId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(monto, clabeDestino, concepto, cuentaOrigenId);
    }

    @Override
    public String toString()
    {
        return "TransferenciaRequest{" +
                "monto=" + monto +
                ", clabeDestino='" + clabeDestino + '\'' +
                ", concepto='" + concepto + '\'' +
                ", cuentaOrigenId=" + cuentaOrigenId +
                '}';
    }
}