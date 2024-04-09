package com.backend.backend.security.service;

import com.backend.backend.security.entity.Rol;

import java.util.Optional;

/**
 * Interfaz que define los métodos para el servicio relacionado con la entidad Rol.
 */
public interface RolService {

    /**
     * Obtiene un Rol por su nombre.
     *
     * @param nombreRol El nombre del Rol a buscar.
     * @return Un objeto Optional que contiene el Rol si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    Optional<Rol> getByNombreRol(String nombreRol);

    /**
     * Obtiene un Rol por su ID.
     *
     * @param id El ID del Rol a buscar.
     * @return Un objeto Optional que contiene el Rol si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    Optional<Rol> getById(int id);

    /**
     * Guarda un Rol en la base de datos.
     *
     * @param rol El Rol a guardar.
     */
    void save(Rol rol);
}
