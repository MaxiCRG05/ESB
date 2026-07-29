package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.retiro.RetiroRequest;
import com.aos.esb.domain.model.retiro.RetiroResponse;
import com.aos.esb.domain.port.outbound.RetiroPort;
import com.aos.esb.infrastructure.outbound.rest.client.RetirosServiceClient;
import org.springframework.stereotype.Component;

@Component
public class RetiroAdapter implements RetiroPort
{
    private final RetirosServiceClient retirosServiceClient;

    public RetiroAdapter(RetirosServiceClient retirosServiceClient)
    {
        this.retirosServiceClient = retirosServiceClient;
    }

    @Override
    public RetiroResponse ejecutarRetiro(RetiroRequest request)
    {
        return retirosServiceClient.realizarRetiro(request);
    }
}