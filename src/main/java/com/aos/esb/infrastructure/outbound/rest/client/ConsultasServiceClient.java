package com.aos.esb.infrastructure.outbound.rest.client;

import com.aos.esb.domain.model.consulta.ConsultaRequest;
import com.aos.esb.domain.model.consulta.ConsultaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "consultas-service", url = "${services.consultas.url}")
public interface ConsultasServiceClient
{
    @PostMapping("/api/v1/consultas")
    ConsultaResponse realizarConsulta(@RequestBody ConsultaRequest request);
}