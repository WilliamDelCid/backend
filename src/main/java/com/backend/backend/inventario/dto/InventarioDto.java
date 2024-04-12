package com.backend.backend.inventario.dto;

public record InventarioDto(
        Long id,
        String nombreProducto,
        String descripcion,
        boolean producto,
        Long unidad,
        int cantidadProducto,
        boolean estadoProducto,
        Long tipoProducto) {
}