package com.backend.backend.orden.servicesImpl;

import com.backend.backend.orden.entities.DetalleMateriaPrima;
import com.backend.backend.orden.repositories.DetalleMateriaPrimaRepository;
import com.backend.backend.orden.services.DetalleMateriaPrimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DetalleMateriaPrimaServiceImpl implements DetalleMateriaPrimaService {

    private final DetalleMateriaPrimaRepository detalleMateriaPrimaRepository;

    @Override
    public Page<DetalleMateriaPrima> listAll(Pageable pageable) {
        return detalleMateriaPrimaRepository.findAll(pageable);
    }


}
