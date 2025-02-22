package com.backend.backend.orden.services;

import com.backend.backend.orden.dto.ConsultaOrdenesDto;
import com.backend.backend.orden.dto.ProduccionDto;
import com.backend.backend.orden.dto.ProduccionFinalizadaDto;
import com.backend.backend.orden.entities.Produccion;
import com.backend.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProduccionService {

    Page<Produccion> listAll(Pageable pageable);

    Mensaje save(ProduccionDto produccionDto);

    Mensaje finalizarProduccion(Long id,ProduccionFinalizadaDto produccionFinalizadaDto);

    List<ConsultaOrdenesDto> listEstadoFechaEspera(Integer estado,Date fechaEsperada);

}
