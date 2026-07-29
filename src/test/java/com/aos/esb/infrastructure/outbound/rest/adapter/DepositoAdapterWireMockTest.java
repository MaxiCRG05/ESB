package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class DepositoAdapterWireMockTest
{
    @Autowired
    private DepositoAdapter adapter;

    @Test
    void shouldCallWireMockAndGetResponse()
    {
        stubFor(post(urlEqualTo("/api/v1/depositos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"transaccionId\": 123, \"montoCantidad\": 2000.00, \"montoMoneda\": \"MXN\"}")));

        DepositoRequest request = new DepositoRequest();
        DepositoResponse response = adapter.ejecutarDeposito(request);

        assertThat(response.getTransaccionId()).isEqualTo(123L);
        verify(postRequestedFor(urlEqualTo("/api/v1/depositos")));
    }
}