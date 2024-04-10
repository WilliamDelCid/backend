package com.backend.backend.orden.servicesImpl;

import com.backend.backend.orden.dto.TipoProductoDto;
import com.backend.backend.orden.entities.TipoProducto;
import com.backend.backend.orden.repositories.TipoProductoRepository;
import com.backend.backend.orden.services.TipoProductoService;
import com.backend.backend.security.dto.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoProductoServiceImpl implements TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;

    @Override
    public List<TipoProducto> listAllProductos() {
        return tipoProductoRepository.findAll();
    }

    @Override
    public Mensaje save(TipoProductoDto tipoProductoDto) {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setNombreTipo(tipoProductoDto.tipoProducto());
        tipoProductoRepository.save(tipoProducto);
        return new Mensaje("Tipo Producto: "+ tipoProducto.getNombreTipo()+ " Agregado con exito");
    }
}
