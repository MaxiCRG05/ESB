package com.aos.esb.domain.port.outbound;

import com.aos.esb.domain.model.transferencia.TransferenciaRequest;
import com.aos.esb.domain.model.transferencia.TransferenciaResponse;

public interface EjecucionTransferenciaPort
{
    TransferenciaResponse ejecutarTransferencia(TransferenciaRequest request);
}