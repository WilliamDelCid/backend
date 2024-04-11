package com.backend.backend.inventario.dto;

public interface ConsultaInventarioDto {
    String getProducto();
    String getDescripcion();
    String getTipoProducto();
    String getUnidad();
    int getCantidadDisponible();

}
