package com.aos.esb.application.usecases;

import com.aos.esb.domain.model.ESBRequest;
import com.aos.esb.domain.model.ESBResponse;
import com.aos.esb.domain.model.Header;
import com.aos.esb.domain.model.consulta.ConsultaRequest;
import com.aos.esb.domain.model.consulta.ConsultaResponse;
import com.aos.esb.domain.model.deposito.DepositoRequest;
import com.aos.esb.domain.model.deposito.DepositoResponse;
import com.aos.esb.domain.model.retiro.RetiroRequest;
import com.aos.esb.domain.model.retiro.RetiroResponse;
import com.aos.esb.domain.model.transferencia.TransferenciaRequest;
import com.aos.esb.domain.model.transferencia.TransferenciaResponse;
import com.aos.esb.domain.port.inbound.ESBOrquestadorPort;
import com.aos.esb.domain.port.outbound.ConsultaPort;
import com.aos.esb.domain.port.outbound.DepositoPort;
import com.aos.esb.domain.port.outbound.EjecucionTransferenciaPort;
import com.aos.esb.domain.port.outbound.RetiroPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class ESBOrquestadorImpl implements ESBOrquestadorPort
{
    private final DepositoPort depositoPort;
    private final RetiroPort retiroPort;
    private final EjecucionTransferenciaPort transferenciaPort;
    private final ConsultaPort consultaPort;
    private final ObjectMapper objectMapper;

    public ESBOrquestadorImpl(DepositoPort depositoPort,
                              RetiroPort retiroPort,
                              EjecucionTransferenciaPort transferenciaPort,
                              ConsultaPort consultaPort,
                              ObjectMapper objectMapper)
    {
        this.depositoPort = depositoPort;
        this.retiroPort = retiroPort;
        this.transferenciaPort = transferenciaPort;
        this.consultaPort = consultaPort;
        this.objectMapper = objectMapper;
    }

    @Override
    public ESBResponse procesarPeticion(ESBRequest request, Integer usuarioId)
    {
        Header header = request.getHeader();
        String tipoOperacion = header.getTipoOperacion();
        JsonNode body = request.getBody();

        try
        {
            return switch (tipoOperacion)
            {
                case "DEPOSITO" -> procesarDeposito(header, body, usuarioId);
                case "RETIRO" -> procesarRetiro(header, body, usuarioId);
                case "TRANSFERENCIA" -> procesarTransferencia(header, body, usuarioId);
                case "CONSULTA" -> procesarConsulta(header, body, usuarioId);
                default -> construirError(header, "400", "Operación no soportada: " + tipoOperacion);
            };
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error interno del servidor: " + e.getMessage());
        }
    }

    private ESBResponse procesarDeposito(Header header, JsonNode body, Integer usuarioId)
    {
        DepositoRequest depositoRequest = objectMapper.convertValue(body, DepositoRequest.class);
        DepositoResponse response = depositoPort.ejecutarDeposito(depositoRequest);
        return construirExito(header, "Depósito realizado con éxito", response);
    }

    private ESBResponse procesarRetiro(Header header, JsonNode body, Integer usuarioId)
    {
        RetiroRequest retiroRequest = objectMapper.convertValue(body, RetiroRequest.class);
        RetiroResponse response = retiroPort.ejecutarRetiro(retiroRequest);
        return construirExito(header, "Retiro realizado con éxito", response);
    }

    private ESBResponse procesarTransferencia(Header header, JsonNode body, Integer usuarioId)
    {
        TransferenciaRequest transferenciaRequest = objectMapper.convertValue(body, TransferenciaRequest.class);
        TransferenciaResponse response = transferenciaPort.ejecutarTransferencia(transferenciaRequest);
        return construirExito(header, "Transferencia realizada con éxito", response);
    }

    private ESBResponse procesarConsulta(Header header, JsonNode body, Integer usuarioId)
    {
        ConsultaRequest consultaRequest = objectMapper.convertValue(body, ConsultaRequest.class);
        ConsultaResponse response = consultaPort.ejecutarConsulta(consultaRequest);

        if (response.isExito())
        {
            return construirExito(header, response.getMensaje(), response.getDatos());
        }
        else
        {
            return construirError(header, response.getCodigo(), response.getMensaje());
        }
    }

    private ESBResponse construirExito(Header headerRequest, String mensaje, Object datos)
    {
        Header responseHeader = new Header();
        responseHeader.setIdCorrelacion(headerRequest.getIdCorrelacion());
        responseHeader.setTimestamp(Instant.now().toString());
        responseHeader.setVersion(headerRequest.getVersion());

        ESBResponse.Body body = new ESBResponse.Body();
        body.setCodigoEstatus("200");
        body.setExito(true);
        body.setMensaje(mensaje);
        body.setDatos(datos);

        ESBResponse response = new ESBResponse();
        response.setHeader(responseHeader);
        response.setBody(body);
        return response;
    }

    private ESBResponse construirError(Header headerRequest, String codigo, String mensaje)
    {
        Header responseHeader = new Header();
        responseHeader.setIdCorrelacion(headerRequest != null ? headerRequest.getIdCorrelacion() : null);
        responseHeader.setTimestamp(Instant.now().toString());
        responseHeader.setVersion(headerRequest != null ? headerRequest.getVersion() : "1.0");

        ESBResponse.Body body = new ESBResponse.Body();
        body.setCodigoEstatus(codigo);
        body.setExito(false);
        body.setMensaje(mensaje);
        body.setDatos(null);

        ESBResponse response = new ESBResponse();
        response.setHeader(responseHeader);
        response.setBody(body);
        return response;
    }
}