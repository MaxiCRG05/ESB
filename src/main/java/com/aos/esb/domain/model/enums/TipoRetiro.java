package com.aos.esb.domain.model.enums;

public enum TipoRetiro
{
    CON_TARJETA,
    SIN_TARJETA;

    public static TipoRetiro fromString(String tipo)
    {
        for (TipoRetiro t : TipoRetiro.values())
            if (t.name().equalsIgnoreCase(tipo.replace(" ", "_")))
                return t;

        throw new IllegalArgumentException("Tipo de retiro no soportado: " + tipo);
    }
}