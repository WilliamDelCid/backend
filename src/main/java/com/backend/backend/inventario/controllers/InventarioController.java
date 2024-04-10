package com.backend.backend.inventario.controllers;


import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.services.InventarioService;
import com.backend.backend.security.dto.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<Page<Inventario>> listar(Pageable pageable) {
        return new ResponseEntity<>(inventarioService.listar(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mensaje> agregar(@RequestBody InventarioDto inventarioDto) {
        return new ResponseEntity<>(inventarioService.agregar(inventarioDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id, @RequestBody InventarioDto inventarioDto) {
        return new ResponseEntity<>(inventarioService.editar(id, inventarioDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(inventarioService.eliminar(id), HttpStatus.OK);
    }
}
