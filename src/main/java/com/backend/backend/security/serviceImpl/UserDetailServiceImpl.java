package com.backend.backend.security.serviceImpl;

import com.backend.backend.security.entity.MainUsuario;
import com.backend.backend.security.entity.Usuario;
import com.backend.backend.security.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio UserDetailsService para cargar detalles del usuario.
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles del usuario por nombre de usuario o correo electrónico.
     *
     * @param nombreOrEmail El nombre de usuario o correo electrónico del usuario.
     * @return Los detalles del usuario cargado.
     * @throws UsernameNotFoundException Si no se encuentra el usuario por el nombre de usuario o correo electrónico proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String nombreOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
        return MainUsuario.build(usuario);
    }
}
