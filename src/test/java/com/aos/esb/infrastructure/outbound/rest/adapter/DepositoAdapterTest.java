package com.aos.esb.infrastructure.outbound.rest.adapter;

import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;
import com.aos.esb.infrastructure.outbound.rest.client.DepositosServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositoAdapterTest
{
    @Mock
    private DepositosServiceClient client;

    @InjectMocks
    private DepositoAdapter adapter;

    @Test
    void shouldCallClientAndReturnResponse()
    {
        DepositoRequest request = new DepositoRequest();
        DepositoResponse expected = new DepositoResponse();
        expected.setTransaccionId(1L);

        when(client.realizarDeposito(any(DepositoRequest.class))).thenReturn(expected);

        DepositoResponse actual = adapter.ejecutarDeposito(request);

        assertThat(actual).isSameAs(expected);
    }
}