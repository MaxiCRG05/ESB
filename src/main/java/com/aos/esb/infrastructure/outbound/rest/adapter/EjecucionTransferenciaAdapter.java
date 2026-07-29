package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.transferencia.TransferenciaRequest;
import com.aos.esb.domain.model.transferencia.TransferenciaResponse;
import com.aos.esb.domain.port.outbound.EjecucionTransferenciaPort;
import com.aos.esb.infrastructure.outbound.rest.client.TransferenciasServiceClient;
import org.springframework.stereotype.Component;

@Component
public class EjecucionTransferenciaAdapter implements EjecucionTransferenciaPort
{
    private final TransferenciasServiceClient transferenciasServiceClient;

    public EjecucionTransferenciaAdapter(TransferenciasServiceClient transferenciasServiceClient)
    {
        this.transferenciasServiceClient = transferenciasServiceClient;
    }

    @Override
    public TransferenciaResponse ejecutarTransferencia(TransferenciaRequest request)
    {
        return transferenciasServiceClient.ejecutarTransferencia(request);
    }
}