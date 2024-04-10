package com.backend.backend.inventario.repositories;

import com.backend.backend.inventario.entities.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnidadRepository extends JpaRepository<Unidad,Long> {
}
