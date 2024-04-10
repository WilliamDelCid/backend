package com.backend.backend.orden.services;

import com.backend.backend.orden.dto.TipoProductoDto;
import com.backend.backend.orden.entities.TipoProducto;
import com.backend.backend.security.dto.Mensaje;

import java.util.List;

public interface TipoProductoService {
    List<TipoProducto> listAllProductos();

    Mensaje save(TipoProductoDto tipoProductoDto);
}
