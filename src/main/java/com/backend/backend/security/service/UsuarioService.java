package com.backend.backend.security.service;

import com.backend.backend.security.dto.JwtDto;
import com.backend.backend.security.dto.LoginUsuario;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.dto.NuevoUsuario;
import com.backend.backend.security.entity.Usuario;

import java.text.ParseException;
import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> getByNombreUsuario(String nombreUsuario);

    Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail);

    Optional<Usuario> getByTokenPassword(String tokenPassword);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByEmail(String email);

    JwtDto login(LoginUsuario loginUsuario);

    JwtDto refresh(JwtDto jwtDto) throws ParseException;

    Mensaje save(NuevoUsuario nuevoUsuario);
}