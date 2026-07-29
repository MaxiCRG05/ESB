package com.aos.esb.domain.model.deposito;

import com.aos.esb.domain.model.enums.MetodoDeposito;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositoRequest
{
    @NotNull(message = "El método de depósito es obligatorio")
    private MetodoDeposito metodo;

    @Valid
    @NotNull(message = "El monto es obligatorio")
    private Monto monto;

    @NotNull(message = "La CLABE destino es obligatoria")
    private String clabeDestino;

    private String referencia;
    private String concepto;

    public DepositoRequest() { }

    public DepositoRequest(MetodoDeposito metodo, Monto monto, String clabeDestino,
                           String referencia, String concepto)
    {
        this.metodo = metodo;
        this.monto = monto;
        this.clabeDestino = clabeDestino;
        this.referencia = referencia;
        this.concepto = concepto;
    }

    public MetodoDeposito getMetodo()
    {
        return metodo;
    }

    public void setMetodo(MetodoDeposito metodo)
    {
        this.metodo = metodo;
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

    public String getReferencia()
    {
        return referencia;
    }

    public void setReferencia(String referencia)
    {
        this.referencia = referencia;
    }

    public String getConcepto()
    {
        return concepto;
    }

    public void setConcepto(String concepto)
    {
        this.concepto = concepto;
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
        DepositoRequest that = (DepositoRequest) o;
        return metodo == that.metodo &&
                Objects.equals(monto, that.monto) &&
                Objects.equals(clabeDestino, that.clabeDestino) &&
                Objects.equals(referencia, that.referencia) &&
                Objects.equals(concepto, that.concepto);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(metodo, monto, clabeDestino, referencia, concepto);
    }

    @Override
    public String toString()
    {
        return "DepositoRequest{" +
                "metodo=" + metodo +
                ", monto=" + monto +
                ", clabeDestino='" + clabeDestino + '\'' +
                ", referencia='" + referencia + '\'' +
                ", concepto='" + concepto + '\'' +
                '}';
    }
}