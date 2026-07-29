package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.consulta.ConsultaRequest;
import com.aos.esb.domain.model.consulta.ConsultaResponse;
import com.aos.esb.domain.port.outbound.ConsultaPort;
import com.aos.esb.infrastructure.outbound.rest.client.ConsultasServiceClient;
import org.springframework.stereotype.Component;

@Component
public class ConsultaAdapter implements ConsultaPort
{
    private final ConsultasServiceClient consultasServiceClient;

    public ConsultaAdapter(ConsultasServiceClient consultasServiceClient)
    {
        this.consultasServiceClient = consultasServiceClient;
    }

    @Override
    public ConsultaResponse ejecutarConsulta(ConsultaRequest request)
    {
        return consultasServiceClient.realizarConsulta(request);
    }
}