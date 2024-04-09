package com.backend.backend.security.service;

import com.backend.backend.security.entity.Rol;

import java.util.Optional;

public interface RolService {
    Optional<Rol> getByNombreRol(String nombreRol);
    Optional<Rol> getById(int id);

    void save(Rol rol);
}