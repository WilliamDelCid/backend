package com.backend.backend.inventario.services;

import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventarioService {

    Page<Inventario> listar(Pageable pageable);

    Mensaje agregar(InventarioDto itemDTO);

    Mensaje editar(Long id, InventarioDto itemDTO);

    Mensaje eliminar(Long id);
}
