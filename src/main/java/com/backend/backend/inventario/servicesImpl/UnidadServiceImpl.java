package com.backend.backend.inventario.servicesImpl;


import com.backend.backend.inventario.entities.Unidad;
import com.backend.backend.inventario.repositories.UnidadRepository;
import com.backend.backend.inventario.services.UnidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnidadServiceImpl implements UnidadService {

    private final UnidadRepository unidadRepository;

    @Override
    public Optional<Unidad> findById(Long id) {
        return unidadRepository.findById(id);
    }
}
