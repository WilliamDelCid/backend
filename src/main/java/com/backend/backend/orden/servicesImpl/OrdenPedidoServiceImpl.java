package com.backend.backend.orden.servicesImpl;

import com.backend.backend.orden.dto.OrdenPedidoDto;
import com.backend.backend.orden.entities.Cliente;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.orden.entities.TipoProducto;
import com.backend.backend.orden.repositories.ClienteRepository;
import com.backend.backend.orden.repositories.OrdenPedidoRepository;
import com.backend.backend.orden.repositories.TipoProductoRepository;
import com.backend.backend.orden.services.OrdenPedidoService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrdenPedidoServiceImpl implements OrdenPedidoService {

    private final OrdenPedidoRepository ordenPedidoRepository;
    private final ClienteRepository clienteRepository;
    private final TipoProductoRepository tipoProductoRepository;

    @Override
    public Page<OrdenPedido> listAll(Pageable pageable) {
        return ordenPedidoRepository.findAll(pageable);
    }

    @Override
    public OrdenPedido findById(Long id) {
        return ordenPedidoRepository.findById(id).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"No se encontro registro con ese ID"));
    }

    @Override
    public Mensaje save(OrdenPedidoDto ordenPedidoDto) {
        try {
            // Create a new OrdenPedido entity and map properties from OrdenPedidoDto
            OrdenPedido ordenPedido = new OrdenPedido();
            Cliente cliente = clienteRepository.findById(ordenPedidoDto.idCliente()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"No se encontro registro con ese ID"));
            ordenPedido.setCliente(cliente);
            ordenPedido.setFechaEsperada(ordenPedidoDto.fechaEsperada());
            TipoProducto tipoProducto = tipoProductoRepository.findById(ordenPedidoDto.idTipoProducto()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"No se encontro registro con ese ID "));
            ordenPedido.setTipoProducto(tipoProducto);
            ordenPedido.setCantidadProducto(ordenPedidoDto.cantidad());
            ordenPedido.setEstadoOrden(ordenPedidoDto.estado());
            ordenPedidoRepository.save(ordenPedido);
            return new Mensaje("Order guardada con exito.");
        } catch (Exception e) {
            throw new CustomException(HttpStatus.CONFLICT, "No se pudo guardar el pedido");
        }
    }

}