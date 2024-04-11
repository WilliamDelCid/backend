package com.backend.backend.orden.controllers;

import com.backend.backend.orden.dto.ProduccionDto;
import com.backend.backend.orden.entities.Produccion;
import com.backend.backend.orden.services.ProduccionService;
import com.backend.backend.security.dto.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produccion")
@RequiredArgsConstructor
public class ProduccionController {

    private final ProduccionService produccionService;

    @PostMapping("/guardar")
    public ResponseEntity<Mensaje> guardarProduccion(@RequestBody ProduccionDto produccionDto) {
        Mensaje mensaje = produccionService.save(produccionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Produccion>> listarProducciones(Pageable pageable) {
        Page<Produccion> producciones = produccionService.listAll(pageable);
        return ResponseEntity.ok(producciones);
    }
}
