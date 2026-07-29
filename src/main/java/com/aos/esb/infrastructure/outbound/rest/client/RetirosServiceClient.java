package com.aos.esb.infrastructure.outbound.rest.client;

import com.aos.esb.domain.model.retiro.RetiroRequest;
import com.aos.esb.domain.model.retiro.RetiroResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "retiros-service", url = "${services.retiros.url}")
public interface RetirosServiceClient
{
    @PostMapping("/api/v1/retiros")
    RetiroResponse realizarRetiro(@RequestBody RetiroRequest request);
}