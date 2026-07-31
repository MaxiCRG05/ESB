package com.aos.esb.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class CuentaCompletaDTO
{
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("CLABE")
    private String clabe;
    @JsonProperty("Saldo")
    private BigDecimal saldo;
    @JsonProperty("Moneda")
    private String moneda;
    @JsonProperty("Estado")
    private String estado;
    @JsonProperty("Tarjetas")
    private List<TarjetaDTO> tarjetas;

    public CuentaCompletaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getClabe() { return clabe; }
    public void setClabe(String clabe) { this.clabe = clabe; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<TarjetaDTO> getTarjetas() { return tarjetas; }
    public void setTarjetas(List<TarjetaDTO> tarjetas) { this.tarjetas = tarjetas; }
}