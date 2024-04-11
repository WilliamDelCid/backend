package com.backend.backend.orden.repositories;

import com.backend.backend.orden.entities.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion,Long> {
}
