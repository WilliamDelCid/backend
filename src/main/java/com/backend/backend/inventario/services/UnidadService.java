package com.backend.backend.inventario.services;

import com.backend.backend.inventario.entities.Unidad;

import java.util.List;
import java.util.Optional;

public interface UnidadService {

    Optional<Unidad> findById(Long id);

    List<Unidad> listAllUnidad();

}
