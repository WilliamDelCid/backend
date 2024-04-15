package com.backend.backend.security;

import com.backend.backend.security.jwt.JwtEntryPoint;
import com.backend.backend.security.jwt.JwtTokenFilter;
import com.backend.backend.security.serviceImpl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración principal para la seguridad de la aplicación.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class MainSecurity {

    private final UserDetailServiceImpl userDetailsServiceImpl;

    private final JwtEntryPoint jwtEntryPoint;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenFilter jwtTokenFilter;

    AuthenticationManager authenticationManager;

    /**
     * Configura el filtro de seguridad y la cadena de filtros de seguridad para la aplicación.
     *
     * @param http El objeto HttpSecurity para configurar la seguridad HTTP.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Sí hay un error durante la configuración de la seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
        authenticationManager = builder.build();
        http.authenticationManager(authenticationManager);

        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**").permitAll()
                .anyRequest().authenticated());
        http.exceptionHandling(exc -> exc.authenticationEntryPoint(jwtEntryPoint));
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
