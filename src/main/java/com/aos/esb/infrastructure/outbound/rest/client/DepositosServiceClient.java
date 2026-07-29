package com.aos.esb.infrastructure.outbound.rest.client;

import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "depositos-service", url = "${services.depositos.url}")
public interface DepositosServiceClient
{
    @PostMapping("/api/v1/depositos")
    DepositoResponse realizarDeposito(@RequestBody DepositoRequest request);
}