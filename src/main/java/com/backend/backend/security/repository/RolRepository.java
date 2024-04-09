package com.backend.backend.security.repository;

import com.backend.backend.security.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Rol.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos para la entidad Rol.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Busca un Rol por su nombre.
     *
     * @param nombreRol El nombre del Rol a buscar.
     * @return Un objeto Optional que contiene el Rol si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    Optional<Rol> findByNombreRol(String nombreRol);

}
