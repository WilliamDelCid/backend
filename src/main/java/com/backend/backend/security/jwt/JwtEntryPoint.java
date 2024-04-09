package com.backend.backend.security.jwt;

import com.backend.backend.security.dto.Mensaje;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Clase para manejar la respuesta de inicio de autenticación JWT.
 * Personaliza la respuesta en caso de una autenticación no exitosa.
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    /**
     * Método para manejar la respuesta de inicio de autenticación JWT.
     * Personaliza la respuesta enviada al cliente en caso de autenticación no exitosa.
     *
     * @param req La solicitud HTTP.
     * @param res La respuesta HTTP.
     * @param e   La excepción de autenticación.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {
        logger.error("Error en el método commence", e);
        Mensaje mensaje = new Mensaje("Token inválido o vacío. Inicie sesión para acceder a esta ruta.");
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(mensaje));
        res.getWriter().flush();
    }
}
