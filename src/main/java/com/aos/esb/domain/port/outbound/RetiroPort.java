package com.aos.esb.domain.port.outbound;

import com.aos.esb.domain.model.retiro.RetiroRequest;
import com.aos.esb.domain.model.retiro.RetiroResponse;

public interface RetiroPort
{
    RetiroResponse ejecutarRetiro(RetiroRequest request);
}