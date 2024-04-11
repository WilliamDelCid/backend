package com.backend.backend.orden.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_produccion")
public class Produccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produccion", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    private OrdenPedido ordenPedido;

    @Column(nullable = false,name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @Column(nullable = false,name = "linea_produccion")
    private boolean lineaProduccion;

    @Column(nullable = false,name = "estado_produccion")
    private boolean estadoProduccion;


}
