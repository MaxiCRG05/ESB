package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.autenticacion.ValidacionTokenRequest;
import com.aos.esb.domain.model.autenticacion.ValidacionTokenResponse;
import com.aos.esb.domain.port.outbound.AutenticacionPort;
import com.aos.esb.infrastructure.outbound.rest.client.AutenticacionServiceClient;
import org.springframework.stereotype.Component;

@Component
public class AutenticacionAdapter implements AutenticacionPort
{
    private final AutenticacionServiceClient client;

    public AutenticacionAdapter(AutenticacionServiceClient client)
    {
        this.client = client;
    }

    @Override
    public ValidacionTokenResponse validarToken(String token)
    {
        ValidacionTokenRequest request = new ValidacionTokenRequest(token);
        return client.validarToken(request);
    }
}