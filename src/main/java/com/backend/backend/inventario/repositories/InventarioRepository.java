package com.backend.backend.inventario.repositories;

import com.backend.backend.inventario.dto.ConsultaInventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long> {
    @Query("SELECT i FROM Inventario i WHERE i.estadoProducto = true")
    Page<Inventario> findAllActive(Pageable pageable);
    @Query(value = "SELECT i.nombre_producto AS producto, i.descripcion AS descripcion, CASE i.tipo_producto WHEN 0 THEN 'Producto terminado' WHEN 1 THEN 'Materia prima' ELSE 'Otro tipo' END as tipoProducto, u.nombre_unidad as unidad, i.cantidad_producto as cantidadDisponible FROM tb_inventario i INNER JOIN tb_unidad u ON u.id_unidad = i.id_unidad",nativeQuery = true)
    List<ConsultaInventarioDto> listAll();

}
