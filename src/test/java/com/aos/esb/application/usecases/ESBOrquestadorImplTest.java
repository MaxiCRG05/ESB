package com.aos.esb.application.usecases;

import com.aos.esb.domain.model.ESBRequest;
import com.aos.esb.domain.model.ESBResponse;
import com.aos.esb.domain.model.Header;
import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;
import com.aos.esb.domain.model.retiro.RetiroResponse;
import com.aos.esb.domain.model.transferencia.TransferenciaRequest;
import com.aos.esb.domain.model.transferencia.TransferenciaResponse;
import com.aos.esb.domain.port.outbound.ConsultaPort;
import com.aos.esb.domain.port.outbound.DepositoPort;
import com.aos.esb.domain.port.outbound.EjecucionTransferenciaPort;
import com.aos.esb.domain.port.outbound.RetiroPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ESBOrquestadorImplTest
{
    @Mock
    private DepositoPort depositoPort;

    @Mock
    private RetiroPort retiroPort;

    @Mock
    private EjecucionTransferenciaPort transferenciaPort;

    @Mock
    private ConsultaPort consultaPort;

    private ESBOrquestadorImpl orquestador;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Header header;
    private final Integer usuarioId = 123;

    @BeforeEach
    void setUp()
    {
        orquestador = new ESBOrquestadorImpl(depositoPort, retiroPort, transferenciaPort, consultaPort, objectMapper);
        header = new Header();
        header.setIdCorrelacion("test-123");
        header.setVersion("1.0");
        header.setTimestamp("2025-06-12T12:00:00Z");
    }

    @Test
    @DisplayName("DEPOSITO exitoso")
    void shouldProcessDepositSuccessfully() throws Exception
    {
        header.setTipoOperacion("DEPOSITO");
        ESBRequest request = new ESBRequest();
        request.setHeader(header);
        String bodyJson = """
                {
                  "metodo": "EFECTIVO",
                  "monto": { "cantidad": 2000.00, "moneda": "MXN" },
                  "clabeDestino": "012345678901234567",
                  "referencia": "DEP-RED-992",
                  "concepto": "Depósito en cajero RED"
                }
                """;
        request.setBody(objectMapper.readTree(bodyJson));

        DepositoResponse mockResponse = new DepositoResponse();
        mockResponse.setTransaccionId(1L);
        mockResponse.setMontoCantidad(BigDecimal.valueOf(2000));
        when(depositoPort.ejecutarDeposito(any(DepositoRequest.class))).thenReturn(mockResponse);

        ESBResponse response = orquestador.procesarPeticion(request, usuarioId);

        assertThat(response.getBody().isExito()).isTrue();
        assertThat(response.getBody().getCodigoEstatus()).isEqualTo("200");
        assertThat(response.getBody().getMensaje()).isEqualTo("Depósito realizado con éxito");
        assertThat(response.getBody().getDatos()).isEqualTo(mockResponse);
    }

    @Test
    @DisplayName("RETIRO exitoso")
    void shouldProcessRetiroSuccessfully() throws Exception
    {
        header.setTipoOperacion("RETIRO");
        ESBRequest request = new ESBRequest();
        request.setHeader(header);
        String bodyJson = """
                {
                  "tipoRetiro": "CON_TARJETA",
                  "monto": { "cantidad": 1500.00, "moneda": "MXN" },
                  "concepto": "Retiro en efectivo"
                }
                """;
        request.setBody(objectMapper.readTree(bodyJson));

        RetiroResponse mockResponse = new RetiroResponse();
        mockResponse.setTransaccionId(2L);
        when(retiroPort.ejecutarRetiro(any())).thenReturn(mockResponse);

        ESBResponse response = orquestador.procesarPeticion(request, usuarioId);
        assertThat(response.getBody().isExito()).isTrue();
        assertThat(response.getBody().getMensaje()).isEqualTo("Retiro realizado con éxito");
    }

    @Test
    @DisplayName("TRANSFERENCIA exitosa")
    void shouldProcessTransferenciaSuccessfully() throws Exception
    {
        header.setTipoOperacion("TRANSFERENCIA");
        ESBRequest request = new ESBRequest();
        request.setHeader(header);
        String bodyJson = """
                {
                  "monto": { "cantidad": 500.00, "moneda": "MXN" },
                  "clabeDestino": "987654321098765432",
                  "concepto": "Pago de cena"
                }
                """;
        request.setBody(objectMapper.readTree(bodyJson));

        TransferenciaResponse mockResponse = new TransferenciaResponse();
        mockResponse.setTransferenciaId(3L);
        when(transferenciaPort.ejecutarTransferencia(any(TransferenciaRequest.class))).thenReturn(mockResponse);

        ESBResponse response = orquestador.procesarPeticion(request, usuarioId);
        assertThat(response.getBody().isExito()).isTrue();
        assertThat(response.getBody().getMensaje()).isEqualTo("Transferencia realizada con éxito");
    }

    @Test
    @DisplayName("Operación no soportada → 400")
    void shouldReturnErrorForUnsupportedOperation() throws Exception
    {
        header.setTipoOperacion("INVALIDO");
        ESBRequest request = new ESBRequest();
        request.setHeader(header);
        request.setBody(objectMapper.createObjectNode());

        ESBResponse response = orquestador.procesarPeticion(request, usuarioId);

        assertThat(response.getBody().isExito()).isFalse();
        assertThat(response.getBody().getCodigoEstatus()).isEqualTo("400");
        assertThat(response.getBody().getMensaje()).contains("Operación no soportada");
    }

    @Test
    @DisplayName("Excepción interna → 500")
    void shouldReturnErrorOnException() throws Exception
    {
        header.setTipoOperacion("DEPOSITO");
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

        when(depositoPort.ejecutarDeposito(any(DepositoRequest.class)))
                .thenThrow(new RuntimeException("Error simulado en el servicio de depósitos"));

        ESBResponse response = orquestador.procesarPeticion(request, usuarioId);

        assertThat(response.getBody().isExito()).isFalse();
        assertThat(response.getBody().getCodigoEstatus()).isEqualTo("500");
        assertThat(response.getBody().getMensaje()).contains("Error interno del servidor");
    }
}