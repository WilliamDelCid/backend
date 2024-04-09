package com.backend.backend.security.serviceImpl;

import com.backend.backend.security.dto.JwtDto;
import com.backend.backend.security.dto.LoginUsuario;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.dto.NuevoUsuario;
import com.backend.backend.security.entity.Rol;
import com.backend.backend.security.entity.Usuario;
import com.backend.backend.security.exceptions.CustomException;
import com.backend.backend.security.jwt.JwtProvider;
import com.backend.backend.security.repository.UsuarioRepository;
import com.backend.backend.security.service.RolService;
import com.backend.backend.security.service.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RolService rolService;

    private final JwtProvider jwtProvider;

    @Override
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail) {
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail);
    }

    @Override
    public Optional<Usuario> getByTokenPassword(String tokenPassword) {
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public JwtDto login(LoginUsuario loginUsuario) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new JwtDto(jwt);
    }

    @Override
    public JwtDto refresh(JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        return new JwtDto(token);
    }

    @Override
    public Mensaje save(NuevoUsuario nuevoUsuario) {
        if (usuarioRepository.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese nombre de usuario ya existe");
        if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese email de usuario ya existe");

        // Buscar el rol por su ID
        Optional<Rol> rolOptional = rolService.getById(nuevoUsuario.getRolId());
        Rol rolUsuario = rolOptional.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No se encontró el rol con el ID proporcionado"));

        // Crear el usuario con el rol asignado
        Usuario usuario = new Usuario(
                nuevoUsuario.getNombre(),
                nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword())
        );
        usuario.setRol(rolUsuario);

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        return new Mensaje(usuario.getNombreUsuario() + " ha sido creado");
    }

}