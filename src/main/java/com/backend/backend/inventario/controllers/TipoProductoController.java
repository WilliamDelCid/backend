package com.backend.backend.inventario.controllers;

import com.backend.backend.inventario.dto.TipoProductoDto;
import com.backend.backend.inventario.entities.TipoProducto;
import com.backend.backend.inventario.services.TipoProductoService;
import com.backend.backend.security.dto.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-producto")
@RequiredArgsConstructor
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProducto>> listarTiposProducto() {
        List<TipoProducto> tiposProducto = tipoProductoService.listAllProductos();
        return new ResponseEntity<>(tiposProducto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mensaje> agregarTipoProducto(@RequestBody TipoProductoDto tipoProductoDto) {
        Mensaje mensaje = tipoProductoService.save(tipoProductoDto);
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }
}
