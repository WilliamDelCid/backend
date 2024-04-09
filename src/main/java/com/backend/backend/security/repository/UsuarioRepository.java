package com.backend.backend.security.repository;

import com.backend.backend.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos para la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un Usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario del Usuario a buscar.
     * @return Un objeto Optional que contiene el Usuario si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca un Usuario por su nombre de usuario o su dirección de correo electrónico.
     *
     * @param nombreUsuario El nombre de usuario del Usuario a buscar.
     * @param email La dirección de correo electrónico del Usuario a buscar.
     * @return Un objeto Optional que contiene el Usuario si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);

    /**
     * Busca un Usuario por su token de restablecimiento de contraseña.
     *
     * @param tokenPassword El token de restablecimiento de contraseña del Usuario a buscar.
     * @return Un objeto Optional que contiene el Usuario si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    Optional<Usuario> findByTokenPassword(String tokenPassword);

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
}
