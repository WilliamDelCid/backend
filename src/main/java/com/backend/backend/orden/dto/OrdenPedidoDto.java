package com.backend.backend.orden.dto;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record OrdenPedidoDto(
        Long idCliente,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaEsperada,
        int cantidad,
        int estado,
        Long idInventario,
        List<DetalleMateriaPrimaDto> detallesMateriaPrima
) {
}
