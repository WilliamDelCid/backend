package com.backend.backend.security.controller;

import com.backend.backend.security.dto.JwtDto;
import com.backend.backend.security.dto.LoginUsuario;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.dto.NuevoUsuario;
import com.backend.backend.security.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

/**
 * Controlador para las operaciones de autenticación de usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    /**
     * Maneja la solicitud de registro de un nuevo usuario.
     *
     * @param nuevoUsuario Los datos del nuevo usuario a registrar.
     * @return ResponseEntity que contiene un mensaje de éxito o error.
     */
    @PostMapping("/nuevo")
    public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario){
        return ResponseEntity.ok(usuarioService.save(nuevoUsuario));
    }

    /**
     * Maneja la solicitud de inicio de sesión de un usuario.
     *
     * @param loginUsuario Las credenciales del usuario para iniciar sesión.
     * @return ResponseEntity que contiene un objeto JwtDto si la autenticación es exitosa.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario){
        return ResponseEntity.ok(usuarioService.login(loginUsuario));
    }

    /**
     * Maneja la solicitud de actualización de un token JWT expirado.
     *
     * @param jwtDto El token JWT expirado.
     * @return ResponseEntity que contiene un nuevo objeto JwtDto con el nuevo token JWT generado.
     * @throws ParseException Si ocurre un error al analizar el token JWT.
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
        return ResponseEntity.ok(usuarioService.refresh(jwtDto));
    }
}
