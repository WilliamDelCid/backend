package com.backend.backend.security.jwt;

import com.backend.backend.security.dto.JwtDto;
import com.backend.backend.security.entity.MainUsuario;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que proporciona funcionalidad para generar, validar y refrescar tokens JWT.
 */
@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    /**
     * Genera un token JWT para la autenticación del usuario.
     *
     * @param authentication La autenticación del usuario.
     * @return El token JWT generado.
     */
    public String generateToken(Authentication authentication) {
        MainUsuario usuarioPrincipal = (MainUsuario) authentication.getPrincipal();
        List<String> rol = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .claim("roles", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 180L))
                .signWith(getSecret(secret))
                .compact();
    }

    /**
     * Obtiene el nombre de usuario a partir de un token JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario extraído del token.
     */
    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Válida la autenticidad y vigencia de un token JWT.
     *
     * @param token El token JWT a validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío");
        } catch (SignatureException e) {
            logger.error("Error en la firma");
        }
        return false;
    }

    /**
     * Refresca un token JWT expirado.
     *
     * @param jwtDto El token JWT a refrescar.
     * @return El nuevo token JWT generado.
     * @throws ParseException Si ocurre un error al analizar el token JWT.
     */
    public String refreshToken(JwtDto jwtDto) throws ParseException {
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(jwtDto.getToken());
        } catch (ExpiredJwtException e) {
            JWT jwt = JWTParser.parse(jwtDto.getToken());
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            String nombreUsuario = claims.getSubject();
            List<String> roles = (List<String>) claims.getClaim("roles");

            return Jwts.builder()
                    .setSubject(nombreUsuario)
                    .claim("roles", roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expiration))
                    .signWith(getSecret(secret))
                    .compact();
        }
        return null;
    }

    /**
     * Obtiene la clave secreta para firmar y verificar los tokens JWT.
     *
     * @param secret La cadena de texto que representa la clave secreta.
     * @return La clave secreta convertida en una instancia de Key.
     */
    private Key getSecret(String secret){
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
