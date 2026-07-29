package com.aos.esb.infrastructure.outbound.rest.client;

import com.aos.esb.domain.model.transferencia.TransferenciaRequest;
import com.aos.esb.domain.model.transferencia.TransferenciaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transferencias-service", url = "${services.transferencias.url}")
public interface TransferenciasServiceClient
{
    @PostMapping("/api/v1/transferencias/")
    TransferenciaResponse ejecutarTransferencia(@RequestBody TransferenciaRequest request);
}