INSERT INTO `tb_cliente` VALUES (1, 'María Fernández');
INSERT INTO `tb_cliente` VALUES (2, 'Juan López');
INSERT INTO `tb_cliente` VALUES (3, 'Laura Martínez');
INSERT INTO `tb_cliente` VALUES (4, 'Pedro Gutiérrez');
INSERT INTO `tb_cliente` VALUES (5, 'Sofía Rodríguez');

INSERT INTO `tb_rol` VALUES (1, 'ROLE_PRODUCCION');
INSERT INTO `tb_rol` VALUES (2, 'ROLE_BODEGA');

INSERT INTO `tb_tipo_producto` VALUES (1, 'Materia Prima');
INSERT INTO `tb_tipo_producto` VALUES (2, 'Camisas');
INSERT INTO `tb_tipo_producto` VALUES (3, 'Gorras');
INSERT INTO `tb_tipo_producto` VALUES (4, 'Uniformes deportivos');

INSERT INTO `tb_unidad` VALUES (1, 'Cajas');
INSERT INTO `tb_unidad` VALUES (2, 'Fardos');
INSERT INTO `tb_unidad` VALUES (3, 'Unidades');
INSERT INTO `tb_unidad` VALUES (4, 'Rollos');
INSERT INTO `tb_unidad` VALUES (5, 'Cubetas');

INSERT INTO `tb_inventario` VALUES (1, 1000, 'Algodón ', b'1', 'Algodón', b'1', 1, 4);
INSERT INTO `tb_inventario` VALUES (2, 1000, 'Lana ', b'1', 'Lana ', b'1', 1, 2);
INSERT INTO `tb_inventario` VALUES (3, 1000, 'Poliéster ', b'1', 'Poliéster ', b'1', 1, 2);
INSERT INTO `tb_inventario` VALUES (4, 1000, 'Seda ', b'1', 'Seda ', b'1', 1, 4);
INSERT INTO `tb_inventario` VALUES (5, 1000, 'Lino ', b'1', 'Lino ', b'1', 1, 4);
INSERT INTO `tb_inventario` VALUES (6, 1000, 'Nylon ', b'1', 'Nylon ', b'1', 1, 2);
INSERT INTO `tb_inventario` VALUES (7, 1000, 'Spandex ', b'1', 'Spandex ', b'1', 1, 2);
INSERT INTO `tb_inventario` VALUES (8, 1000, 'Cuero ', b'1', 'Cuero ', b'1', 1, 4);
INSERT INTO `tb_inventario` VALUES (9, 1000, 'Etiquetas', b'1', 'Etiquetas', b'1', 1, 2);
INSERT INTO `tb_inventario` VALUES (10, 0, 'Camisas ', b'1', 'Camisas ', b'0', 2, 3);
INSERT INTO `tb_inventario` VALUES (11, 0, 'Pantalones ', b'1', 'Pantalones ', b'0', 4, 3);
INSERT INTO `tb_inventario` VALUES (12, 0, 'Gorras ', b'1', 'Gorras ', b'0', 3, 3);

INSERT INTO `tb_usuario` VALUES (1, 'produccion@gmail.com', 'Ana María García', 'produccion', '$2a$10$KKfoSoQAtqY6M7K3E1Mx5e9YNBCFydQynb45.u9XnZegeJmVOjeSC', NULL, 1);
INSERT INTO `tb_usuario` VALUES (2, 'bodega@gmail.com', 'Carlos Pérez', 'bodega', '$2a$10$FyQ1WXHYUfF40.0sv04zxuxdWnLv2ueuD9Lcz4kIDLhwRdaMZgv2u', NULL, 2);
