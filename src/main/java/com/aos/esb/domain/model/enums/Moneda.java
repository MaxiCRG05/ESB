package com.aos.esb.domain.model.enums;

public enum Moneda
{
    MXN,
    USD,
    EUR;

    public static Moneda fromString(String moneda)
    {
        for (Moneda m : Moneda.values())
            if (m.name().equalsIgnoreCase(moneda))
                return m;

        throw new IllegalArgumentException("Moneda no soportada: " + moneda);
    }
}