package com.backend.backend.orden.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ProduccionDto(
        Long idOrdenPedido,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaIngreso,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaFinalizacion,
        boolean lineaProduccion,
        boolean estadoProduccion
) {
}
