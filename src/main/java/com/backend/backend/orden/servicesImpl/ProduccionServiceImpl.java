package com.backend.backend.orden.servicesImpl;


import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.orden.dto.ConsultaOrdenesDto;
import com.backend.backend.orden.dto.ProduccionDto;
import com.backend.backend.orden.dto.ProduccionFinalizadaDto;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.orden.entities.Produccion;
import com.backend.backend.orden.repositories.OrdenPedidoRepository;
import com.backend.backend.orden.repositories.ProduccionRepository;
import com.backend.backend.orden.services.ProduccionService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduccionServiceImpl implements ProduccionService {

    private final ProduccionRepository produccionRepository;
    private final OrdenPedidoRepository ordenPedidoRepository;
    private final InventarioRepository inventarioRepository;

    @Override
    public Page<Produccion> listAll(Pageable pageable) {
        return produccionRepository.findAll(pageable);
    }

    @Override
    public Mensaje save(ProduccionDto produccionDto) {
        Produccion produccion = new Produccion();

        if (produccionRepository.existsByOrdenPedidoId(produccionDto.idOrdenPedido())) {
            throw new CustomException(HttpStatus.CONFLICT, "La orden ya existe en la producción");
        }

        OrdenPedido ordenPedido = ordenPedidoRepository.findById(produccionDto.idOrdenPedido())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        ordenPedido.setEstadoOrden(1);
        produccion.setOrdenPedido(ordenPedido);
        produccion.setFechaIngreso(produccionDto.fechaIngreso());
        produccion.setLineaProduccion(produccionDto.lineaProduccion());
        produccion.setEstadoProduccion(false);

        ordenPedidoRepository.save(ordenPedido);
        produccionRepository.save(produccion);

        return new Mensaje("Producción guardada con éxito.");
    }


    @Override
    public Mensaje finalizarProduccion(Long id, ProduccionFinalizadaDto produccionFinalizadaDto) {
        Produccion produccion = produccionRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No se encontró producción con ese ID"));

        if (produccion.isEstadoProduccion() || produccion.getFechaFinalizacion() != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "La producción ya ha sido finalizada anteriormente.");
        }
        produccion.setFechaFinalizacion(produccionFinalizadaDto.fechaFinalizacion());
        produccion.setEstadoProduccion(true);
        OrdenPedido ordenPedido = produccion.getOrdenPedido();
        ordenPedido.setEstadoOrden(2);
        Inventario inventario = ordenPedido.getInventario();
        int cantidadSumar = ordenPedido.getCantidadProducto();
        int cantidadExistente = inventario.getCantidadProducto();
        int nuevoValorInventario = cantidadExistente + cantidadSumar;
        inventario.setCantidadProducto(nuevoValorInventario);
        ordenPedidoRepository.save(ordenPedido);
        inventarioRepository.save(inventario);
        produccionRepository.save(produccion);
        return new Mensaje("Producción finalizada con éxito.");
    }

    @Override
    public List<ConsultaOrdenesDto> listEstadoFechaEspera(Integer estado, Date fechaEsperada) {
        return produccionRepository.findByEstadoAndFechaEsperada(estado, fechaEsperada);
    }


}
