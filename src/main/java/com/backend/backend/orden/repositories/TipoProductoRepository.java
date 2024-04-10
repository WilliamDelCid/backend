package com.backend.backend.orden.repositories;

import com.backend.backend.orden.entities.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto,Long> {

}
