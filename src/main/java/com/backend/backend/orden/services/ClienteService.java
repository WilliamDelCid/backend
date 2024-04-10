package com.backend.backend.orden.services;

import com.backend.backend.orden.dto.ClienteDto;
import com.backend.backend.orden.entities.Cliente;
import com.backend.backend.security.dto.Mensaje;

import java.util.List;

public interface ClienteService {

    List<Cliente> listAllClientes();

    Mensaje save(ClienteDto clienteDto);

}
