package com.backend.backend.orden.services;

import com.backend.backend.orden.dto.OrdenPedidoDto;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdenPedidoService {

    Page<OrdenPedido> listAll(Pageable pageable);

    OrdenPedido findById(Long id);

    Mensaje save(OrdenPedidoDto ordenPedidoDto);

    List<OrdenPedido> listAll();


}
