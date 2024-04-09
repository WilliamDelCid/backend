package com.backend.backend.security.serviceImpl;

import com.backend.backend.security.entity.Rol;
import com.backend.backend.security.repository.RolRepository;
import com.backend.backend.security.service.RolService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public Optional<Rol> getByNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    @Override
    public Optional<Rol> getById(int id) {
        return rolRepository.findById(id);
    }

    @Override
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

}
