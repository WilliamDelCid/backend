package com.backend.backend.inventario.servicesImpl;

import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.entities.TipoProducto;
import com.backend.backend.inventario.entities.Unidad;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.inventario.repositories.TipoProductoRepository;
import com.backend.backend.inventario.services.InventarioService;
import com.backend.backend.inventario.services.UnidadService;
import com.backend.backend.orden.repositories.DetalleMateriaPrimaRepository;
import com.backend.backend.orden.repositories.OrdenPedidoRepository;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final UnidadService unidadService;
    private final TipoProductoRepository tipoProductoRepository;
    private final DetalleMateriaPrimaRepository detalleMateriaPrimaRepository;
    private final OrdenPedidoRepository ordenPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Inventario> listar(String nombreProducto,Pageable pageable) {
        return inventarioRepository.findAllActive(nombreProducto,pageable);
    }

    @Override
    @Transactional
    public Mensaje agregar(InventarioDto itemDTO) {
        try {
            Inventario inventario = convertirAEntidad(itemDTO);
            inventarioRepository.save(inventario);
            return new Mensaje("Inventario agregado con exito");
        } catch (Exception e) {
            throw new CustomException(HttpStatus.CONFLICT, "Ocurrió un error al ingresar el inventario");
        }
    }

    @Override
    public Inventario buscar(Long id) {
        return inventarioRepository.findById(id).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND, "No se encontro inventario con ese ID"));
    }

    @Override
    @Transactional
    public Mensaje editar(Long id, InventarioDto itemDTO) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setDescripcion(itemDTO.descripcion());
            inventario.setCantidadProducto(itemDTO.cantidadProducto());
            inventarioRepository.save(inventario);
            return new Mensaje("Item editado con éxito.");
        }).orElseThrow(()->new CustomException(HttpStatus.CONFLICT, "El inventario con id: "+id+ " No fue encontrado" ));
    }

    @Override
    @Transactional
    public Mensaje eliminar(Long id) {
        return inventarioRepository.findById(id).map(inventarioBase -> {
            Inventario inventario = inventarioRepository.findById(id).orElse(null);
            if (inventario == null) {
                throw new RuntimeException("El producto de inventario no existe");
            }
            if (detalleMateriaPrimaRepository.countByInventario(inventario) > 0) {
                throw new CustomException(HttpStatus.CONFLICT,"No se puede eliminar el producto de inventario porque está asociado a registros en DetalleMateriaPrima");
            }

            if (ordenPedidoRepository.countByInventario(inventario) > 0) {
                throw new CustomException(HttpStatus.CONFLICT,"No se puede eliminar el producto de inventario porque está asociado a registros en OrdenPedido");
            }
            inventarioBase.setEstadoProducto(false);
            inventarioRepository.save(inventarioBase);
            return new Mensaje("Inventario eliminado con éxito.");
        }).orElseThrow(()->new CustomException(HttpStatus.CONFLICT, "No se encontró el item con id: " + id));
    }

    @Override
    public List<Inventario> listTipo(Long idTipoProducto) {
        return inventarioRepository.findByTipoProductoIdAndProductoIsTrue(idTipoProducto);
    }


    private Inventario convertirAEntidad(InventarioDto itemDTO) {
        Inventario inventario = new Inventario();
        inventario.setDescripcion(itemDTO.descripcion());
        inventario.setCantidadProducto(itemDTO.cantidadProducto());
        inventario.setEstadoProducto(true);
        inventario.setNombreProducto(itemDTO.nombreProducto());
        inventario.setProducto(itemDTO.producto());
        TipoProducto tipoProducto = tipoProductoRepository.findById(itemDTO.tipoProducto()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"No se encontro registro con ese ID "));
        inventario.setTipoProducto(tipoProducto);
        Unidad unidad = unidadService.findById(itemDTO.unidad()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"La unidad solicitada no existe"));
        inventario.setUnidad(unidad);
        return inventario;
    }


}
