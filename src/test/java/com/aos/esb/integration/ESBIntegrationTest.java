package com.aos.esb.integration;

import com.aos.esb.domain.model.ESBRequest;
import com.aos.esb.domain.model.Header;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class ESBIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldProcessDepositFullStack() throws Exception
    {
        Header header = new Header();
        header.setIdCorrelacion("integ-test");
        header.setTipoOperacion("DEPOSITO");
        header.setVersion("1.0");

        ESBRequest request = new ESBRequest();
        request.setHeader(header);
        String bodyJson = """
        {
          "metodo": "EFECTIVO",
          "monto": { "cantidad": 500.00, "moneda": "MXN" },
          "clabeDestino": "012345678901234567"
        }
        """;
        request.setBody(objectMapper.readTree(bodyJson));

        // Stub para el servicio de depósitos
        stubFor(post(urlEqualTo("/api/v1/depositos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"transaccionId\": 99, \"montoCantidad\": 500.00, \"montoMoneda\": \"MXN\"}")));

        // Stub para autenticación (filtro JWT)
        stubFor(post(urlEqualTo("/api/Autenticacion/validar-token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"valido\": true, \"usuarioId\": 123, \"correo\": \"test@mail.com\"}")));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/esb/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer validToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.exito").value(true))
                .andExpect(jsonPath("$.body.datos.transaccionId").value(99));
    }
}