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

/**
 * Implementación del servicio para la gestión de usuarios.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RolService rolService;
    private final JwtProvider jwtProvider;

    /**
     * Verifica si existe un usuario con el nombre de usuario proporcionado.
     *
     * @param nombreUsuario El nombre de usuario a verificar.
     * @return true si existe un usuario con el nombre de usuario proporcionado, false de lo contrario.
     */
    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    /**
     * Verifica si existe un usuario con el correo electrónico proporcionado.
     *
     * @param email El correo electrónico a verificar.
     * @return true si existe un usuario con el correo electrónico proporcionado, false de lo contrario.
     */
    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Auténtica a un usuario y genera un token JWT.
     *
     * @param loginUsuario Las credenciales del usuario para iniciar sesión.
     * @return Un objeto JwtDto que contiene el token JWT generado.
     */
    @Override
    public JwtDto login(LoginUsuario loginUsuario) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new JwtDto(jwt);
    }

    /**
     * Actualiza un token JWT expirado.
     *
     * @param jwtDto El token JWT expirado.
     * @return Un objeto JwtDto que contiene el nuevo token JWT generado.
     * @throws ParseException Sí ocurre un error al analizar la fecha de expiración del token JWT.
     */
    @Override
    public JwtDto refresh(JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        return new JwtDto(token);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nuevoUsuario Los datos del nuevo usuario a registrar.
     * @return Un objeto Mensaje que indica el resultado del proceso de registro.
     */
    @Override
    public Mensaje save(NuevoUsuario nuevoUsuario) {
        if (existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya está en uso");
        if (existsByEmail(nuevoUsuario.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "El correo electrónico ya está en uso");
        Optional<Rol> rolOptional = rolService.getById(nuevoUsuario.getRolId());
        Rol rolUsuario = rolOptional.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        Usuario usuario = new Usuario(
                nuevoUsuario.getNombre(),
                nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword())
        );
        usuario.setRol(rolUsuario);
        usuarioRepository.save(usuario);
        return new Mensaje(usuario.getNombreUsuario() + " ha sido registrado exitosamente");
    }

}
