package com.backend.backend.inventario.controllers;


import com.backend.backend.inventario.entities.Unidad;
import com.backend.backend.inventario.services.UnidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidad")
@RequiredArgsConstructor
public class UnidadController {

    private final UnidadService unidadService;

    @GetMapping
    public ResponseEntity<List<Unidad>> listar() {
        return new ResponseEntity<>(unidadService.listAllUnidad(), HttpStatus.OK);
    }

}
