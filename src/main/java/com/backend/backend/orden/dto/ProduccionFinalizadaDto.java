package com.backend.backend.orden.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ProduccionFinalizadaDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaFinalizacion
) {
}
