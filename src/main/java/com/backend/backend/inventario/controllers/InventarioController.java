package com.backend.backend.inventario.controllers;


import com.backend.backend.inventario.dto.InventarioDto;
import com.backend.backend.inventario.entities.Inventario;
import com.backend.backend.inventario.repositories.InventarioRepository;
import com.backend.backend.inventario.services.InventarioService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.utils.PdfUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;
    private final InventarioRepository inventarioRepository;
    private final PdfUtils pdfUtils;

    @GetMapping
    public ResponseEntity<Page<Inventario>> listar(Pageable pageable) {
        return new ResponseEntity<>(inventarioService.listar(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mensaje> agregar(@RequestBody InventarioDto inventarioDto) {
        return new ResponseEntity<>(inventarioService.agregar(inventarioDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(inventarioService.buscar(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id, @RequestBody InventarioDto inventarioDto) {
        return new ResponseEntity<>(inventarioService.editar(id, inventarioDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(inventarioService.eliminar(id), HttpStatus.OK);
    }

    @GetMapping("/generar-pdf")
    public ResponseEntity<byte[]> generarPDF() throws Exception {
        List<?> lista1 = inventarioRepository.listAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista1);
        Map<String,Object> parameters = new HashMap<>();
        String rutaJRXML = "/reports/reporteInventario.jrxml";
        String nombreArchivo = "reporte de prueba.pdf";
        return pdfUtils.generarPDF(nombreArchivo,rutaJRXML, parameters, dataSource);
    }

}
