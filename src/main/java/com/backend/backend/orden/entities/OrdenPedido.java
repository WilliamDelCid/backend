package com.backend.backend.orden.entities;


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
@Table(name = "tb_orden_producto")
public class OrdenPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(nullable = false,name = "fecha_esperada")
    private Date fechaEsperada;

    @ManyToOne
    @JoinColumn(name = "id_tipo_producto")
    private TipoProducto tipoProducto;


    @Column(nullable = false,name = "cantidad_producto")
    private int cantidadProducto;

    @Column(nullable = false,name = "estado_orden")
    private boolean estadoOrden;

}
