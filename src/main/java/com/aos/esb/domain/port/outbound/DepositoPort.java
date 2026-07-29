package com.aos.esb.domain.port.outbound;

import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;

public interface DepositoPort
{
    DepositoResponse ejecutarDeposito(DepositoRequest request);
}