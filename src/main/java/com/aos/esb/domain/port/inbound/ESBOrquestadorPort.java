package com.aos.esb.domain.port.inbound;

import com.aos.esb.domain.model.ESBRequest;
import com.aos.esb.domain.model.ESBResponse;

public interface ESBOrquestadorPort
{
    ESBResponse procesarPeticion(ESBRequest request, Integer usuarioId);
}