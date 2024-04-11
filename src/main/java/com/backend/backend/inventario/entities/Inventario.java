package com.backend.backend.inventario.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario", nullable = false)
    private Long id;

    @Column(nullable = false,name = "nombre_producto")
    private String nombreProducto;

    @Column(nullable = false,name = "descripcion")
    private String descripcion;

    @Column(nullable = false,name = "tipo_producto")
    private boolean producto;

    @ManyToOne
    @JoinColumn(name = "id_unidad")
    private Unidad unidad;

    @Column(nullable = false,name = "cantidad_producto")
    private int cantidadProducto;

    @Column(nullable = false,name = "estado_producto")
    private boolean estadoProducto;

    @ManyToOne
    @JoinColumn(name = "id_tipo_producto")
    private TipoProducto tipoProducto;

}
