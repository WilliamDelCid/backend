package com.backend.backend.security.jwt;

import com.backend.backend.security.serviceImpl.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para manejar y validar los tokens JWT en cada solicitud HTTP.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailServiceImpl userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    /**
     * Método para procesar las solicitudes HTTP y validar los tokens JWT.
     *
     * @param request         La solicitud HTTP.
     * @param response         La respuesta HTTP.
     * @param filterChain El filtro de cadena.
     * @throws ServletException Sí ocurre un error durante la manipulación de la solicitud.
     * @throws IOException      Sí ocurre un error durante la manipulación de la solicitud o la respuesta.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (token != null && jwtProvider.validateToken(token)) {
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error("Error en el método doFilter: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT de la cabecera de autorización de la solicitud.
     *
     * @param request La solicitud HTTP.
     * @return El token JWT extraído de la cabecera de autorización.
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
