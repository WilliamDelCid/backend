package com.backend.backend.orden.repositories;

import com.backend.backend.orden.entities.DetalleMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleMateriaPrimaRepository extends JpaRepository<DetalleMateriaPrima,Long> {
}
