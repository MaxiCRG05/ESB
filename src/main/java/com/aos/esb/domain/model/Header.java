package com.aos.esb.domain.model;

import lombok.Data;

@Data
public class Header
{
    private String idCorrelacion;
    private String aplicacionOrigen;
    private String timestamp;
    private String token;
    private String tipoOperacion;
    private String version;
}