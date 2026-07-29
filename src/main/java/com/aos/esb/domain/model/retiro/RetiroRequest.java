package com.aos.esb.domain.model.retiro;

import com.aos.esb.domain.model.enums.TipoRetiro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetiroRequest
{
    @NotNull(message = "El tipo de retiro es obligatorio")
    private TipoRetiro tipoRetiro;

    @Valid
    @NotNull(message = "El monto es obligatorio")
    private Monto monto;

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long cuentaId;

    private String concepto;

    public RetiroRequest() { }

    public RetiroRequest(TipoRetiro tipoRetiro, Monto monto, String concepto, Long cuentaId)
    {
        this.tipoRetiro = tipoRetiro;
        this.monto = monto;
        this.concepto = concepto;
        this.cuentaId = cuentaId;
    }

    public TipoRetiro getTipoRetiro()
    {
        return tipoRetiro;
    }

    public void setTipoRetiro(TipoRetiro tipoRetiro)
    {
        this.tipoRetiro = tipoRetiro;
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

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
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
        RetiroRequest that = (RetiroRequest) o;
        return tipoRetiro == that.tipoRetiro &&
                Objects.equals(monto, that.monto) &&
                Objects.equals(concepto, that.concepto);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tipoRetiro, monto, concepto);
    }

    @Override
    public String toString()
    {
        return "RetiroRequest{" +
                "tipoRetiro=" + tipoRetiro +
                ", monto=" + monto +
                ", concepto='" + concepto + '\'' +
                '}';
    }
}