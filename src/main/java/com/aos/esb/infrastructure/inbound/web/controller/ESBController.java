package com.aos.esb.infrastructure.inbound.web.controller;

import com.aos.esb.domain.model.ESBRequest;
import com.aos.esb.domain.model.ESBResponse;
import com.aos.esb.domain.port.inbound.ESBOrquestadorPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/esb")
public class ESBController
{
    private final ESBOrquestadorPort orquestador;

    public ESBController(ESBOrquestadorPort orquestador)
    {
        this.orquestador = orquestador;
    }

    @PostMapping("/dispatch")
    public ResponseEntity<ESBResponse> dispatch(@RequestBody ESBRequest request,
                                                HttpServletRequest servletRequest)
    {
        Integer usuarioId = (Integer) servletRequest.getAttribute("usuarioId");
        ESBResponse response = orquestador.procesarPeticion(request, usuarioId);
        return ResponseEntity.ok(response);
    }
}