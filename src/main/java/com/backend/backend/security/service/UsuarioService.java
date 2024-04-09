package com.backend.backend.security.service;

import com.backend.backend.security.dto.JwtDto;
import com.backend.backend.security.dto.LoginUsuario;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.dto.NuevoUsuario;
import com.backend.backend.security.entity.Usuario;

import java.text.ParseException;
import java.util.Optional;

/**
 * Interfaz que define los métodos para el servicio relacionado con la entidad Usuario.
 */
public interface UsuarioService {

    /**
     * Verifica si existe un Usuario con el nombre de usuario dado.
     *
     * @param nombreUsuario El nombre de usuario a verificar.
     * @return true si existe un Usuario con el nombre de usuario dado, de lo contrario, false.
     */
    boolean existsByNombreUsuario(String nombreUsuario);

    /**
     * Verifica si existe un Usuario con el correo electrónico dado.
     *
     * @param email El correo electrónico a verificar.
     * @return true si existe un Usuario con el correo electrónico dado, de lo contrario, false.
     */
    boolean existsByEmail(String email);

    /**
     * Auténtica a un usuario y genera un token JWT.
     *
     * @param loginUsuario El objeto que contiene las credenciales del usuario para iniciar sesión.
     * @return Un objeto JwtDto que contiene el token JWT generado.
     */
    JwtDto login(LoginUsuario loginUsuario);

    /**
     * Actualiza un token JWT expirado.
     *
     * @param jwtDto El objeto JwtDto que contiene el token JWT expirado.
     * @return Un objeto JwtDto que contiene el nuevo token JWT generado.
     * @throws ParseException Sí ocurre un error al analizar la fecha de expiración del token JWT.
     */
    JwtDto refresh(JwtDto jwtDto) throws ParseException;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nuevoUsuario El objeto NuevoUsuario que contiene la información del nuevo usuario a registrar.
     * @return Un objeto Mensaje que indica el resultado del proceso de registro.
     */
    Mensaje save(NuevoUsuario nuevoUsuario);
}
