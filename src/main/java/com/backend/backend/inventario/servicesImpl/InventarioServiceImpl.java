package com.backend.backend.inventario.servicesImpl;

import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.entities.Unidad;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.inventario.services.InventarioService;
import com.backend.backend.inventario.services.UnidadService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.security.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final UnidadService unidadService;

    @Override
    @Transactional(readOnly = true)
    public Page<Inventario> listar(Pageable pageable) {
        return inventarioRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mensaje agregar(InventarioDto itemDTO) {
        try {
            Inventario inventario = convertirAEntidad(itemDTO);
            inventarioRepository.save(inventario);
            return new Mensaje("Inventario agregado con exito");
        } catch (Exception e) {
            throw new CustomException(HttpStatus.CONFLICT, "Ocurrio un error al ingresar el inventario");
        }
    }

    @Override
    @Transactional
    public Mensaje editar(Long id, InventarioDto itemDTO) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setDescripcion(itemDTO.descripcion());
            inventario.setCantidadProducto(itemDTO.cantidadProducto());
            inventario.setEstadoProducto(itemDTO.estadoProducto());
            inventarioRepository.save(inventario);
            return new Mensaje("Item editado con éxito.");
        }).orElseThrow(()->new CustomException(HttpStatus.CONFLICT, "El inventario con id: "+id+ " No fue encontrado" ));
    }

    @Override
    @Transactional
    public Mensaje eliminar(Long id) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setEstadoProducto(false);
            inventarioRepository.save(inventario);
            return new Mensaje("Inventario eliminado con éxito.");
        }).orElseThrow(()->new CustomException(HttpStatus.CONFLICT, "No se encontró el item con id: " + id));
    }


    private Inventario convertirAEntidad(InventarioDto itemDTO) {
        Inventario inventario = new Inventario();
        inventario.setDescripcion(itemDTO.descripcion());
        inventario.setCantidadProducto(itemDTO.cantidadProducto());
        inventario.setEstadoProducto(itemDTO.estadoProducto());
        inventario.setNombreProducto(itemDTO.nombreProducto());
        inventario.setTipoProducto(itemDTO.tipoProducto());
        Unidad unidad = unidadService.findById(itemDTO.unidadId()).orElseThrow(()->new CustomException(HttpStatus.CONFLICT,"La unidad solicitada no existe"));
        inventario.setUnidad(unidad);
        return inventario;
    }


}
