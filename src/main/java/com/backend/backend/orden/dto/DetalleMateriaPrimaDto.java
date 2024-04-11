package com.backend.backend.orden.dto;

public record DetalleMateriaPrimaDto(
        Long idOrdenPedido,
        Long idInventario,
        int cantidad
) {
}
