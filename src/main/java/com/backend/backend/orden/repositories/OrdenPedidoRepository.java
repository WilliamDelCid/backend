package com.backend.backend.orden.repositories;

import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.orden.entities.OrdenPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenPedidoRepository extends JpaRepository<OrdenPedido,Long> {

    long countByInventario(Inventario inventario);

}
