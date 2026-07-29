package com.aos.esb.infrastructure.inbound.web.config;

import com.aos.esb.domain.model.ESBResponse;
import com.aos.esb.domain.model.Header;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ESBResponse> handleException(Exception ex, HttpServletRequest request)
    {
        Header header = new Header();
        header.setTimestamp(Instant.now().toString());
        header.setVersion("1.0");
        // Podrías extraer el idCorrelacion del request si está disponible

        ESBResponse.Body body = new ESBResponse.Body();
        body.setCodigoEstatus("500");
        body.setExito(false);
        body.setMensaje("Error interno del servidor: " + ex.getMessage());
        body.setDatos(null);

        ESBResponse response = new ESBResponse(header, body);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ESBResponse> handleFeignException(FeignException ex, HttpServletRequest request)
    {
        Header header = new Header();
        header.setTimestamp(Instant.now().toString());
        header.setVersion("1.0");

        ESBResponse.Body body = new ESBResponse.Body();
        body.setCodigoEstatus(String.valueOf(ex.status()));
        body.setExito(false);
        body.setMensaje("Error en la comunicación con el servicio: " + ex.getMessage());
        body.setDatos(null);

        ESBResponse response = new ESBResponse(header, body);
        return ResponseEntity.status(ex.status()).body(response);
    }
}