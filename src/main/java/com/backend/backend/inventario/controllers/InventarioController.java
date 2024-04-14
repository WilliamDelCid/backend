package com.backend.backend.inventario.controllers;


import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.services.InventarioService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.utils.PdfUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;
    private final PdfUtils pdfUtils;

    @GetMapping
    @PreAuthorize("hasRole('BODEGA') or hasRole('PRODUCCION')")
    public ResponseEntity<Page<Inventario>> listar(@RequestParam(name = "nombreProducto", required = false) String nombreProducto,Pageable pageable) {
        return new ResponseEntity<>(inventarioService.listar(nombreProducto,pageable), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('BODEGA')")
    public ResponseEntity<Mensaje> agregar(@RequestBody InventarioDto inventarioDto) {
        return new ResponseEntity<>(inventarioService.agregar(inventarioDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('BODEGA') or hasRole('PRODUCCION')")
    @GetMapping("/tipo/{idTipoProducto}")
    public List<Inventario> obtenerInventarioPorTipoProductoId(@PathVariable Long idTipoProducto) {
        return inventarioService.listTipo(idTipoProducto);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('PRODUCCION')")
    public List<Inventario> obtenerAll() {
        return inventarioService.listAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('BODEGA')")
    public ResponseEntity<Inventario> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(inventarioService.buscar(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BODEGA')")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id, @RequestBody InventarioDto inventarioDto) {
        return new ResponseEntity<>(inventarioService.editar(id, inventarioDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BODEGA')")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(inventarioService.eliminar(id), HttpStatus.OK);
    }

    @GetMapping("/generar-pdf/{idTipoProducto}")
    @PreAuthorize("hasRole('BODEGA')")
    public ResponseEntity<byte[]> generarPDF(@PathVariable Long idTipoProducto) throws Exception {
        List<?> lista1 = inventarioService.listTipoProducto(idTipoProducto);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista1);
        Map<String,Object> parameters = new HashMap<>();
        String rutaJRXML = "/reports/reporteInventario.jrxml";
        String nombreArchivo = "reporte de prueba.pdf";
        return pdfUtils.generarPDF(nombreArchivo,rutaJRXML, parameters, dataSource);
    }

}
