package com.backend.backend.orden.repositories;

import com.backend.backend.orden.dto.ConsultaOrdenesDto;
import com.backend.backend.orden.entities.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion,Long> {

    @Query(value = "SELECT p.id_orden AS idOrden, c.nombre_cliente AS nombreCliente, DATE_FORMAT(op.fecha_esperada, '%d-%m-%Y') AS fechaEsperada, DATE_FORMAT(p.fecha_ingreso, '%d-%m-%Y') AS fechaIngresoProduccion, DATE_FORMAT(p.fecha_finalizacion, '%d-%m-%Y') AS fechaFinalizacion, CASE p.estado_produccion WHEN 0 THEN 'En producción' WHEN 1 THEN 'Finalizado' ELSE 'Estado desconocido' END AS estado FROM tb_produccion p INNER JOIN tb_orden_producto op ON p.id_orden = op.id_orden INNER JOIN tb_cliente c ON c.id_cliente = op.id_cliente",nativeQuery = true)
    List<ConsultaOrdenesDto> listAll();

}
