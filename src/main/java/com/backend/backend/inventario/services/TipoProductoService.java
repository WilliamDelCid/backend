package com.backend.backend.inventario.services;

import com.backend.backend.inventario.dto.TipoProductoDto;
import com.backend.backend.inventario.entities.TipoProducto;
import com.backend.backend.security.dto.Mensaje;

import java.util.List;

public interface TipoProductoService {
    List<TipoProducto> listAllProductos();

    Mensaje save(TipoProductoDto tipoProductoDto);
}
