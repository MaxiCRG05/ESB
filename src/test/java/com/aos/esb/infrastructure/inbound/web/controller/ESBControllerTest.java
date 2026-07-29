package com.aos.esb.infrastructure.inbound.web.controller;

import com.aos.esb.domain.model.ESBRequest;
import com.aos.esb.domain.model.ESBResponse;
import com.aos.esb.domain.model.Header;
import com.aos.esb.domain.model.autenticacion.ValidacionTokenResponse;
import com.aos.esb.domain.port.inbound.ESBOrquestadorPort;
import com.aos.esb.domain.port.outbound.AutenticacionPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ESBControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ESBOrquestadorPort orquestador;

    @MockBean
    private AutenticacionPort autenticacionPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDispatchAndReturnOk() throws Exception
    {
        Header header = new Header();
        header.setIdCorrelacion("req-123");
        header.setTipoOperacion("DEPOSITO");
        header.setVersion("1.0");

        ESBRequest request = new ESBRequest();
        request.setHeader(header);
        request.setBody(objectMapper.readTree("{\"test\":\"data\"}"));

        ESBResponse mockResponse = new ESBResponse();
        mockResponse.setHeader(header);
        ESBResponse.Body body = new ESBResponse.Body();
        body.setExito(true);
        body.setCodigoEstatus("200");
        mockResponse.setBody(body);

        ValidacionTokenResponse validToken = new ValidacionTokenResponse(true, 123, "test@mail.com", null);
        when(autenticacionPort.validarToken("validToken")).thenReturn(validToken);

        when(orquestador.procesarPeticion(any(ESBRequest.class), eq(123))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/esb/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer validToken")) // Token simulado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.exito").value(true));
    }
}