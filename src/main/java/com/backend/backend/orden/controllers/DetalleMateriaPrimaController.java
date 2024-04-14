package com.backend.backend.orden.controllers;

import com.backend.backend.orden.entities.DetalleMateriaPrima;
import com.backend.backend.orden.services.DetalleMateriaPrimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detalle-materia-prima")
@RequiredArgsConstructor
public class DetalleMateriaPrimaController {

    private final DetalleMateriaPrimaService detalleMateriaPrimaService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('PRODUCCION')")
    public ResponseEntity<Page<DetalleMateriaPrima>> listarDetalleMateriaPrima(Pageable pageable) {
        Page<DetalleMateriaPrima> detalleMateriaPrimaPage = detalleMateriaPrimaService.listAll(pageable);
        return ResponseEntity.ok(detalleMateriaPrimaPage);
    }
}
