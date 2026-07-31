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
import com.aos.esb.domain.model.usuario.*;
import com.aos.esb.domain.port.inbound.ESBOrquestadorPort;
import com.aos.esb.domain.port.outbound.ConsultaPort;
import com.aos.esb.domain.port.outbound.DepositoPort;
import com.aos.esb.domain.port.outbound.EjecucionTransferenciaPort;
import com.aos.esb.domain.port.outbound.RetiroPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ESBOrquestadorImpl implements ESBOrquestadorPort
{
    @Value("${services.consultas.url}")
    private String consultasServiceUrl;
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
                case "MOVIMIENTOS" -> procesarMovimientos(header, body, usuarioId);
                case "CONSULTA_USUARIO" -> procesarConsultaUsuario(header, body, usuarioId);
                case "CONSULTA_USUARIO_POR_CLABE" -> procesarConsultaUsuarioPorClabe(header, body, usuarioId);
                case "CONSULTA_USUARIO_POR_TELEFONO" -> procesarConsultaUsuarioPorTelefono(header, body);
                case "CONSULTA_CUENTAS_COMPLETAS" -> procesarConsultaCuentasCompletas(header, body, usuarioId);
                case "GENERAR_CODIGO_RETIRO" -> procesarGenerarCodigoRetiro(header, body, usuarioId);
                case "VALIDAR_CODIGO_RETIRO" -> procesarValidarCodigoRetiro(header, body, usuarioId);
                default -> construirError(header, "400", "Operación no soportada: " + tipoOperacion);
            };
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error interno del servidor: " + e.getMessage() + "\nLocalized Message:" + e.getLocalizedMessage());
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

    private ESBResponse procesarMovimientos(Header header, JsonNode body, Integer usuarioId)
    {
        ConsultaRequest consultaRequest = objectMapper.convertValue(body, ConsultaRequest.class);
        ConsultaResponse response = consultaPort.ejecutarConsulta(consultaRequest);
        return construirExito(header, response.getMensaje(), response.getDatos());
    }

    private ESBResponse procesarConsultaUsuario(Header header, JsonNode body, Integer usuarioId)
    {
        if (usuarioId == null)
            return construirError(header, "401", "Usuario no autenticado");

        try
        {
            String url = consultasServiceUrl + "/api/v1/usuarios/" + usuarioId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + header.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UsuarioResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    UsuarioResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            {
                UsuarioResponse usuario = response.getBody();
                return construirExito(header, "Usuario obtenido correctamente", usuario);
            }
            else
                return construirError(header, "404", "Usuario no encontrado");
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error al obtener usuario: " + e.getMessage());
        }
    }

    private ESBResponse procesarConsultaUsuarioPorTelefono(Header header, JsonNode body)
    {
        try
        {
            if (body == null || !body.has("telefono"))
                return construirError(header, "400", "El teléfono es obligatorio");

            String telefono = body.get("telefono").asText();
            if (telefono == null || telefono.isEmpty())
                return construirError(header, "400", "El teléfono es obligatorio");

            String url = consultasServiceUrl + "/api/v1/usuarios/telefono/" + telefono;
            HttpHeaders headers = new HttpHeaders();
            if (header.getToken() != null && !header.getToken().isEmpty())
                headers.set("Authorization", "Bearer " + header.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UsuarioResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    UsuarioResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            {
                UsuarioResponse usuario = response.getBody();
                return construirExito(header, "Usuario obtenido correctamente por teléfono", usuario);
            }
            else
                return construirError(header, "404", "Usuario no encontrado");
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error al obtener usuario por teléfono: " + e.getMessage());
        }
    }

    private ESBResponse procesarGenerarCodigoRetiro(Header header, JsonNode body, Integer usuarioId)
    {
        if (usuarioId == null)
            return construirError(header, "401", "Usuario no autenticado");

        try
        {
            String url = consultasServiceUrl + "/api/v1/usuarios/" + usuarioId + "/generar-codigo-retiro";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + header.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<GenerarCodigoRetiroResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    GenerarCodigoRetiroResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
                return construirExito(header, response.getBody().getMensaje(), response.getBody());
            else
                return construirError(header, "400", "Error al generar código de retiro");
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error al generar código: " + e.getMessage());
        }
    }

    private ESBResponse procesarValidarCodigoRetiro(Header header, JsonNode body, Integer usuarioId)
    {
        if (body == null || !body.has("codigo"))
            return construirError(header, "400", "El código es obligatorio");


        String codigo = body.get("codigo").asText();
        if (codigo == null || codigo.trim().isEmpty())
            return construirError(header, "400", "El código es obligatorio");

        try
        {
            String url = consultasServiceUrl + "/api/v1/usuarios/codigo/" + codigo.trim();
            HttpHeaders headers = new HttpHeaders();
            if (header.getToken() != null && !header.getToken().isEmpty())
                headers.set("Authorization", "Bearer " + header.getToken());

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UsuarioResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    UsuarioResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
                return construirExito(header, "Código válido", response.getBody());
            else
                return construirError(header, "404", "Código inválido");
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error al validar código: " + e.getMessage());
        }
    }

    private ESBResponse procesarConsultaUsuarioPorClabe(Header header, JsonNode body, Integer usuarioId)
    {
        if (body == null || !body.has("clabe"))
            return construirError(header, "400", "La CLABE es obligatoria");

        String clabe = body.get("clabe").asText();
        if (clabe == null || clabe.trim().isEmpty())
            return construirError(header, "400", "La CLABE es obligatoria");

        try
        {
            HttpHeaders headers = new HttpHeaders();
            if (header.getToken() != null && !header.getToken().isEmpty())
                headers.set("Authorization", "Bearer " + header.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            String urlCuenta = consultasServiceUrl + "/api/v1/cuentas/clabe/" + clabe.trim();
            ResponseEntity<Map> responseCuenta = restTemplate.exchange(
                    urlCuenta,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (!responseCuenta.getStatusCode().is2xxSuccessful() || responseCuenta.getBody() == null)
                return construirError(header, "404", "Cuenta no encontrada para la CLABE: " + clabe);

            Map<String, Object> cuentaData = responseCuenta.getBody();
            Integer usuarioIdEncontrado = (Integer) cuentaData.get("UsuarioId");
            if (usuarioIdEncontrado == null)
                return construirError(header, "404", "La cuenta no tiene un usuario asociado");

            String urlUsuario = consultasServiceUrl + "/api/v1/usuarios/" + usuarioIdEncontrado;
            ResponseEntity<UsuarioResponse> responseUsuario = restTemplate.exchange(
                    urlUsuario,
                    HttpMethod.GET,
                    entity,
                    UsuarioResponse.class
            );

            if (responseUsuario.getStatusCode().is2xxSuccessful() && responseUsuario.getBody() != null)
                return construirExito(header, "Usuario obtenido correctamente por CLABE", responseUsuario.getBody());
            else
                return construirError(header, "404", "Usuario no encontrado para la cuenta");
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error al obtener usuario por CLABE: " + e.getMessage());
        }
    }

    private ESBResponse procesarConsultaCuentasCompletas(Header header, JsonNode body, Integer usuarioId)
    {
        if (usuarioId == null)
            return construirError(header, "401", "Usuario no autenticado");

        try
        {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + header.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String urlCuentas = consultasServiceUrl + "/api/v1/cuentas/usuario/" + usuarioId;
            ResponseEntity<List<Map<String, Object>>> responseCuentas = restTemplate.exchange(
                    urlCuentas,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (!responseCuentas.getStatusCode().is2xxSuccessful() || responseCuentas.getBody() == null)
                return construirError(header, "404", "No se encontraron cuentas para el usuario");

            List<Map<String, Object>> cuentas = responseCuentas.getBody();
            List<CuentaCompletaDTO> cuentasCompletas = new ArrayList<>();

            for (Map<String, Object> cuentaData : cuentas)
            {
                CuentaCompletaDTO cuentaDTO = new CuentaCompletaDTO();
                cuentaDTO.setId(((Number) cuentaData.get("Id")).intValue());
                cuentaDTO.setClabe((String) cuentaData.get("CLABE"));
                cuentaDTO.setSaldo(new BigDecimal(cuentaData.get("SaldoCantidad").toString()));
                cuentaDTO.setMoneda((String) cuentaData.get("SaldoMoneda"));
                cuentaDTO.setEstado((String) cuentaData.get("Estado"));

                Integer cuentaId = cuentaDTO.getId();
                String urlTarjetas = consultasServiceUrl + "/api/v1/tarjetas/cuenta/" + cuentaId;
                ResponseEntity<List<Map<String, Object>>> responseTarjetas = restTemplate.exchange(
                        urlTarjetas,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {}
                );

                List<TarjetaDTO> tarjetasDTO = new ArrayList<>();
                if (responseTarjetas.getStatusCode().is2xxSuccessful() && responseTarjetas.getBody() != null)
                {
                    for (Map<String, Object> tarjetaData : responseTarjetas.getBody())
                    {
                        TarjetaDTO tarjetaDTO = new TarjetaDTO();
                        tarjetaDTO.setId(((Number) tarjetaData.get("Id")).intValue());
                        tarjetaDTO.setNumero((String) tarjetaData.get("Numero"));
                        tarjetaDTO.setEstado((String) tarjetaData.get("Estado"));
                        tarjetaDTO.setFechaExpiracion(tarjetaData.get("FechaExpiracion").toString());
                        tarjetasDTO.add(tarjetaDTO);
                    }
                }
                cuentaDTO.setTarjetas(tarjetasDTO);
                cuentasCompletas.add(cuentaDTO);
            }

            Map<String, Object> datosRespuesta = new HashMap<>();
            datosRespuesta.put("usuarioId", usuarioId);
            datosRespuesta.put("cuentas", cuentasCompletas);

            return construirExito(header, "Consulta de cuentas y tarjetas exitosa", datosRespuesta);
        }
        catch (Exception e)
        {
            return construirError(header, "500", "Error al obtener cuentas y tarjetas: " + e.getMessage());
        }
    }

    private ESBResponse procesarConsulta(Header header, JsonNode body, Integer usuarioId)
    {
        ConsultaRequest consultaRequest = objectMapper.convertValue(body, ConsultaRequest.class);
        ConsultaResponse response = consultaPort.ejecutarConsulta(consultaRequest);

        if (response.isExito())
            return construirExito(header, response.getMensaje(), response.getDatos());
        else
            return construirError(header, response.getCodigo(), response.getMensaje());
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