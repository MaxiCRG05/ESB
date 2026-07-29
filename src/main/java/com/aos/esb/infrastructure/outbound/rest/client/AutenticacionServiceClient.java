package com.aos.esb.infrastructure.outbound.rest.client;

import com.aos.esb.domain.model.autenticacion.ValidacionTokenRequest;
import com.aos.esb.domain.model.autenticacion.ValidacionTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "autenticacion-service", url = "${services.autenticacion.url}")
public interface AutenticacionServiceClient
{
    @PostMapping("/api/Autenticacion/validar-token")
    ValidacionTokenResponse validarToken(@RequestBody ValidacionTokenRequest request);
}