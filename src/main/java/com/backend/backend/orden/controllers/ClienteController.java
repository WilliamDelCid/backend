package com.backend.backend.orden.controllers;

import com.backend.backend.orden.dto.ClienteDto;
import com.backend.backend.orden.entities.Cliente;
import com.backend.backend.orden.services.ClienteService;
import com.backend.backend.security.dto.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @PreAuthorize("hasRole('PRODUCCION')")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('PRODUCCION')")
    public ResponseEntity<Mensaje> agregarCliente(@RequestBody ClienteDto clienteDto) {
        Mensaje mensaje = clienteService.save(clienteDto);
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }
}
