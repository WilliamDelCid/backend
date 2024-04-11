package com.backend.backend.orden.servicesImpl;


import com.backend.backend.orden.dto.ProduccionDto;
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

@Service
@RequiredArgsConstructor
public class ProduccionServiceImpl implements ProduccionService {

    private final ProduccionRepository produccionRepository;
    private final OrdenPedidoRepository ordenPedidoRepository;

    @Override
    public Page<Produccion> listAll(Pageable pageable) {
        return produccionRepository.findAll(pageable);
    }

    @Override
    public Mensaje save(ProduccionDto produccionDto) {
            Produccion produccion = new Produccion();
            OrdenPedido ordenPedido = ordenPedidoRepository.findById(produccionDto.idOrdenPedido())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order no encontrada"));
            produccion.setOrdenPedido(ordenPedido);
            produccion.setFechaIngreso(produccionDto.fechaIngreso());
            produccion.setFechaFinalizacion(produccionDto.fechaFinalizacion());
            produccion.setLineaProduccion(produccionDto.lineaProduccion());
            produccion.setEstadoProduccion(produccionDto.estadoProduccion());
            produccionRepository.save(produccion);
            return new Mensaje("Produccion guardada con exito.");

    }

}
