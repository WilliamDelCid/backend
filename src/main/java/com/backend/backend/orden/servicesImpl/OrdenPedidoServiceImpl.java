package com.backend.backend.orden.servicesImpl;

import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.orden.dto.OrdenPedidoDto;
import com.backend.backend.orden.entities.Cliente;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.inventario.entities.TipoProducto;
import com.backend.backend.orden.repositories.ClienteRepository;
import com.backend.backend.orden.repositories.OrdenPedidoRepository;
import com.backend.backend.inventario.repositories.TipoProductoRepository;
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
    private final InventarioRepository inventarioRepository;
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
            OrdenPedido ordenPedido = new OrdenPedido();
            Cliente cliente = clienteRepository.findById(ordenPedidoDto.idCliente()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"No se encontro registro con ese ID"));
            ordenPedido.setCliente(cliente);
            Inventario inventario = inventarioRepository.findById(ordenPedidoDto.idInventario()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"No se encontro registro con ese ID"));
            ordenPedido.setInventario(inventario);
            ordenPedido.setFechaEsperada(ordenPedidoDto.fechaEsperada());
            ordenPedido.setCantidadProducto(ordenPedidoDto.cantidad());
            ordenPedido.setEstadoOrden(ordenPedidoDto.estado());
            ordenPedidoRepository.save(ordenPedido);
            return new Mensaje("Order guardada con exito.");
        } catch (Exception e) {
            throw new CustomException(HttpStatus.CONFLICT, "No se pudo guardar el pedido");
        }
    }

}