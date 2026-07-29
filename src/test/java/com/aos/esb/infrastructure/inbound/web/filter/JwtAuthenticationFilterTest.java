package com.aos.esb.infrastructure.inbound.web.filter;

import com.aos.esb.domain.model.autenticacion.ValidacionTokenResponse;
import com.aos.esb.domain.port.outbound.AutenticacionPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest
{
    @Mock
    private AutenticacionPort autenticacionPort;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp()
    {
        when(request.getRequestURI()).thenReturn("/api/v1/esb/dispatch");
    }

    @Test
    void shouldAllowHealthEndpointWithoutToken() throws Exception
    {
        when(request.getRequestURI()).thenReturn("/health");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(autenticacionPort, never()).validarToken(anyString());
    }

    @Test
    void shouldRejectRequestWithoutToken() throws Exception
    {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado o formato inválido");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void shouldRejectInvalidToken() throws Exception
    {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        ValidacionTokenResponse invalidResponse = new ValidacionTokenResponse(false, 0, null, "Token inválido");
        when(autenticacionPort.validarToken("invalidToken")).thenReturn(invalidResponse);

        filter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void shouldAcceptValidTokenAndSetAttributes() throws Exception
    {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        ValidacionTokenResponse validResponse = new ValidacionTokenResponse(true, 456, "test@mail.com", null);
        when(autenticacionPort.validarToken("validToken")).thenReturn(validResponse);

        filter.doFilterInternal(request, response, filterChain);

        verify(request).setAttribute("usuarioId", 456);
        verify(request).setAttribute("correo", "test@mail.com");
        verify(filterChain, times(1)).doFilter(request, response);
    }
}