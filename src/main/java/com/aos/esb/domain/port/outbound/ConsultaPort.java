package com.aos.esb.domain.port.outbound;

import com.aos.esb.domain.model.consulta.ConsultaRequest;
import com.aos.esb.domain.model.consulta.ConsultaResponse;

public interface ConsultaPort
{
    ConsultaResponse ejecutarConsulta(ConsultaRequest request);
}