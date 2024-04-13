package com.backend.backend.inventario.services;

import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventarioService {

    Page<Inventario> listar(String nombreProducto,Pageable pageable);

    Mensaje agregar(InventarioDto itemDTO);

    Inventario buscar(Long id);

    Mensaje editar(Long id, InventarioDto itemDTO);

    Mensaje eliminar(Long id);

    List<Inventario> listTipo(Long idTipoProducto);

    List<Inventario> listAll();

}
