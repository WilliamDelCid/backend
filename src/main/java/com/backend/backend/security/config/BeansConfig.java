package com.backend.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración que define los beans necesarios para la seguridad y la autenticación.
 */
@Configuration
public class BeansConfig {

    /**
     * Bean para el codificador de contraseñas BCrypt.
     *
     * @return Un nuevo objeto BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para el administrador de autenticación.
     *
     * @param configuration La configuración de autenticación.
     * @return El administrador de autenticación configurado.
     * @throws Exception Si hay un error al obtener el administrador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
