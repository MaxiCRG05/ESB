package com.aos.esb.infrastructure.inbound.web.filter;

import com.aos.esb.domain.model.autenticacion.ValidacionTokenResponse;
import com.aos.esb.domain.port.outbound.AutenticacionPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final AutenticacionPort autenticacionPort;

    public JwtAuthenticationFilter(AutenticacionPort autenticacionPort)
    {
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {
        String path = request.getRequestURI();
        if (path.startsWith("/health") || path.startsWith("/actuator"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token no proporcionado o formato inválido");
            return;
        }
        String token = authHeader.substring(7);

        try
        {
            ValidacionTokenResponse validation = autenticacionPort.validarToken(token);
            if (!validation.isValido())
            {
                response.sendError(HttpStatus.UNAUTHORIZED.value(),
                        validation.getMensaje() != null ? validation.getMensaje() : "Token inválido");
                return;
            }

            request.setAttribute("usuarioId", validation.getUsuarioId());
            request.setAttribute("correo", validation.getCorreo());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            validation.getUsuarioId(),
                            null,
                            Collections.emptyList()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Error al validar token");
        }
    }
}