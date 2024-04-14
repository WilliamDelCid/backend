package com.backend.backend.orden.servicesImpl;

import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.orden.dto.DetalleMateriaPrimaDto;
import com.backend.backend.orden.dto.OrdenPedidoDto;
import com.backend.backend.orden.entities.Cliente;
import com.backend.backend.orden.entities.DetalleMateriaPrima;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.orden.repositories.ClienteRepository;
import com.backend.backend.orden.repositories.DetalleMateriaPrimaRepository;
import com.backend.backend.orden.repositories.OrdenPedidoRepository;
import com.backend.backend.orden.services.OrdenPedidoService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class OrdenPedidoServiceImpl implements OrdenPedidoService {

    private final OrdenPedidoRepository ordenPedidoRepository;
    private final ClienteRepository clienteRepository;
    private final InventarioRepository inventarioRepository;
    private final DetalleMateriaPrimaRepository detalleMateriaPrimaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<OrdenPedido> listAll(Pageable pageable) {
        return ordenPedidoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public OrdenPedido findById(Long id) {
        return ordenPedidoRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "No se encontro registro con ese ID"));
    }

    @Override
    @Transactional
    public Mensaje save(OrdenPedidoDto ordenPedidoDto) {
        OrdenPedido ordenPedido = new OrdenPedido();
        Cliente cliente = clienteRepository.findById(ordenPedidoDto.idCliente()).orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "No se encontro registro con ese ID"));
        ordenPedido.setCliente(cliente);
        Long idInventarioOrden = ordenPedidoDto.idInventario();

        boolean existeIdInventarioEnDetalles = ordenPedidoDto.detallesMateriaPrima().stream()
                .anyMatch(detalleDto -> Objects.equals(detalleDto.idInventario(), idInventarioOrden));

        if (existeIdInventarioEnDetalles) {
            throw new CustomException(HttpStatus.CONFLICT, "El idInventario de la orden de pedido no puede estar presente en los detalles de materia prima");
        }
        Inventario inventarioEncontrado = inventarioRepository.findById(ordenPedidoDto.idInventario()).orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "No se encontro registro con ese ID"));
        ordenPedido.setInventario(inventarioEncontrado);
        ordenPedido.setFechaEsperada(ordenPedidoDto.fechaEsperada());
        ordenPedido.setCantidadProducto(ordenPedidoDto.cantidad());
        ordenPedido.setEstadoOrden(0);

        if (!verificarDisponibilidadInventario(ordenPedidoDto)) {
            throw new CustomException(HttpStatus.CONFLICT, "La cantidad solicitada de materia prima excede la disponibilidad en inventario");
        }

        ordenPedidoRepository.save(ordenPedido);

        for (DetalleMateriaPrimaDto detalleDto : ordenPedidoDto.detallesMateriaPrima()) {
            DetalleMateriaPrima detalleMateriaPrima = new DetalleMateriaPrima();

            Inventario inventario = inventarioRepository.findById(detalleDto.idInventario())
                    .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "No se encontró registro con ese ID"));
            detalleMateriaPrima.setInventario(inventario);

            detalleMateriaPrima.setCantidadUtilizar(detalleDto.cantidad());
            detalleMateriaPrima.setOrdenPedido(ordenPedido);

            if (inventario.getCantidadProducto() < detalleDto.cantidad() || inventario.getCantidadProducto() == 0) {
                throw new CustomException(HttpStatus.CONFLICT, "La cantidad solicitada de materia prima excede la disponibilidad en inventario o resulta en un inventario igual a cero");
            }

            inventario.setCantidadProducto(inventario.getCantidadProducto() - detalleDto.cantidad());
            inventarioRepository.save(inventario);

            detalleMateriaPrimaRepository.save(detalleMateriaPrima);
        }



        return new Mensaje("Pedido guardado con éxito.");
    }

    @Override
    public List<OrdenPedido> listAll() {
        return ordenPedidoRepository.findAll();
    }


    private boolean verificarDisponibilidadInventario(OrdenPedidoDto ordenPedidoDto) {
        Inventario inventario = inventarioRepository.findById(ordenPedidoDto.idInventario())
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "No se encontró registro con ese ID"));
        return inventario.getCantidadProducto() >= ordenPedidoDto.detallesMateriaPrima().stream()
                .mapToInt(DetalleMateriaPrimaDto::cantidad)
                .sum();
    }


}