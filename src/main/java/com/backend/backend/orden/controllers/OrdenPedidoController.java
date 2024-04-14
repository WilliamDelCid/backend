package com.backend.backend.orden.controllers;

import com.backend.backend.orden.dto.OrdenPedidoDto;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.orden.services.OrdenPedidoService;
import com.backend.backend.security.dto.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenPedidoController {

    private final OrdenPedidoService ordenPedidoService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('PRODUCCION')")
    public ResponseEntity<Mensaje> crearOrden(@RequestBody OrdenPedidoDto ordenPedidoDto) {
        return ResponseEntity.ok(ordenPedidoService.save(ordenPedidoDto));
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('PRODUCCION')")
    public ResponseEntity<Page<OrdenPedido>> listarOrdenes(Pageable pageable) {
        Page<OrdenPedido> ordenes = ordenPedidoService.listAll(pageable);
        return ResponseEntity.ok(ordenes);
    }
    @GetMapping("/listar-all")
    @PreAuthorize("hasRole('PRODUCCION')")
    public ResponseEntity<List<OrdenPedido>> listarOrdenes() {
        List<OrdenPedido> ordenes = ordenPedidoService.listAll();
        return ResponseEntity.ok(ordenes);
    }
}
