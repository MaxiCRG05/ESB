package com.aos.esb.domain.model.enums;

public enum MetodoDeposito
{
    EFECTIVO,
    TRANSFERENCIA_SPEI,
    CHEQUE;

    public static MetodoDeposito fromString(String metodo)
    {
        for (MetodoDeposito m : MetodoDeposito.values())
            if (m.name().equalsIgnoreCase(metodo.replace(" ", "_")))
                return m;
        throw new IllegalArgumentException("Método de depósito no soportado: " + metodo);
    }
}