package com.backend.backend.orden.dto;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record OrdenPedidoDto(
        Long idCliente,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaEsperada,
        Long idTipoProducto,
        int cantidad,
        boolean estado
) {
}
