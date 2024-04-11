package com.backend.backend.orden.services;

import com.backend.backend.orden.dto.DetalleMateriaPrimaDto;
import com.backend.backend.orden.entities.DetalleMateriaPrima;
import com.backend.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DetalleMateriaPrimaService {

    Page<DetalleMateriaPrima> listAll(Pageable pageable);

    Mensaje save(DetalleMateriaPrimaDto dto);

}
