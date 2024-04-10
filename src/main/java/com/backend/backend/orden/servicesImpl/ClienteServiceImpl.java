package com.backend.backend.orden.servicesImpl;


import com.backend.backend.orden.dto.ClienteDto;
import com.backend.backend.orden.entities.Cliente;
import com.backend.backend.orden.repositories.ClienteRepository;
import com.backend.backend.orden.services.ClienteService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;


    @Override
    public List<Cliente> listAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Mensaje save(ClienteDto clienteDto) {
        try{
            Cliente cliente = new Cliente();
            cliente.setNombreCliente(clienteDto.nombreCliente());
            clienteRepository.save(cliente);
            return new Mensaje("Cliente: "+ cliente.getNombreCliente()+" ingresado con exito");
        }catch (Exception e){
            throw new CustomException(HttpStatus.CONFLICT,"Ocurrió un error al ingresar el cliente");
        }
    }
}
