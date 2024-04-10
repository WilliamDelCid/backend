package com.backend.backend.inventario.repositories;

import com.backend.backend.inventario.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long> {
}
