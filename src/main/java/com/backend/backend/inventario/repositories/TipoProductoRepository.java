package com.backend.backend.inventario.repositories;

import com.backend.backend.inventario.entities.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto,Long> {

}
