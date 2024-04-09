package com.backend.backend.security.serviceImpl;

import com.backend.backend.security.entity.Rol;
import com.backend.backend.security.repository.RolRepository;
import com.backend.backend.security.service.RolService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación del servicio para la entidad Rol.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    /**
     * Obtiene un Rol por su ID.
     *
     * @param id El ID del Rol a buscar.
     * @return Un objeto Optional que contiene el Rol si se encuentra, de lo contrario, un objeto Optional vacío.
     */
    @Override
    public Optional<Rol> getById(int id) {
        return rolRepository.findById(id);
    }

    /**
     * Guarda un Rol en la base de datos.
     *
     * @param rol El Rol a guardar.
     */
    @Override
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

}
