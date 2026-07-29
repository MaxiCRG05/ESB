package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;
import com.aos.esb.domain.port.outbound.DepositoPort;
import com.aos.esb.infrastructure.outbound.rest.client.DepositosServiceClient;
import org.springframework.stereotype.Component;

@Component
public class DepositoAdapter implements DepositoPort
{
    private final DepositosServiceClient depositosServiceClient;

    public DepositoAdapter(DepositosServiceClient depositosServiceClient)
    {
        this.depositosServiceClient = depositosServiceClient;
    }

    @Override
    public DepositoResponse ejecutarDeposito(DepositoRequest request)
    {
        return depositosServiceClient.realizarDeposito(request);
    }
}