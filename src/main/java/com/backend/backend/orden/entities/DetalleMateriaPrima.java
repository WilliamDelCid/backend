package com.backend.backend.orden.entities;


import com.backend.backend.inventario.entities.Inventario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_detalle_materia_prima")
public class DetalleMateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia_prima", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    private OrdenPedido ordenPedido;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventario inventario;

    @Column(nullable = false,name = "cantidad_utilizar")
    private int cantidadUtilizar;

}
