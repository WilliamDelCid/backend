package com.backend.backend.orden.dto;

public interface ConsultaOrdenesDto {

    long getIdOrden();
    String getNombreCliente();
    String getFechaEsperada();
    String getFechaIngresoProduccion();
    String getFechaFinalizacion();
    String getEstado();

}
