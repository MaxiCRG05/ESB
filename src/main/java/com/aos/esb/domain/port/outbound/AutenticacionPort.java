package com.aos.esb.domain.port.outbound;

import com.aos.esb.domain.model.autenticacion.ValidacionTokenResponse;

public interface AutenticacionPort
{
    ValidacionTokenResponse validarToken(String token);
}