package com.backend.backend.orden.servicesImpl;

import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.orden.dto.DetalleMateriaPrimaDto;
import com.backend.backend.orden.entities.DetalleMateriaPrima;
import com.backend.backend.orden.entities.OrdenPedido;
import com.backend.backend.orden.repositories.DetalleMateriaPrimaRepository;
import com.backend.backend.orden.repositories.OrdenPedidoRepository;
import com.backend.backend.orden.services.DetalleMateriaPrimaService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DetalleMateriaPrimaServiceImpl implements DetalleMateriaPrimaService {

    private final DetalleMateriaPrimaRepository detalleMateriaPrimaRepository;
    private final OrdenPedidoRepository ordenPedidoRepository;
    private final InventarioRepository inventarioRepository;
    @Override
    public Page<DetalleMateriaPrima> listAll(Pageable pageable) {
        return detalleMateriaPrimaRepository.findAll(pageable);
    }

    @Override
    public Mensaje save(DetalleMateriaPrimaDto dto) {
        try {
            DetalleMateriaPrima detalleMateriaPrima = new DetalleMateriaPrima();
            OrdenPedido ordenPedido = ordenPedidoRepository.findById(dto.idOrdenPedido())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order no encontrada"));
            Inventario inventario = inventarioRepository.findById(dto.idInventario())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));
            detalleMateriaPrima.setOrdenPedido(ordenPedido);
            detalleMateriaPrima.setInventario(inventario);
            detalleMateriaPrima.setCantidadUtilizar(dto.cantidad());
            detalleMateriaPrimaRepository.save(detalleMateriaPrima);

            return new Mensaje("Materia Prima almacenada con exito.");
        } catch (CustomException e) {
            return new Mensaje(e.getMessage());
        } catch (Exception e) {
            return new Mensaje("Error al guardar DetalleMateriaPrima: " + e.getMessage());
        }
    }

}
