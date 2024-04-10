package com.backend.backend.inventario.dto;

public record InventarioDto(
        Long id,
        String nombreProducto,
        String descripcion,
        boolean tipoProducto,
        Long unidadId,
        int cantidadProducto,
        boolean estadoProducto) {
}