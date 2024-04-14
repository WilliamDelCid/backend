package com.backend.backend.orden.controllers;

import com.backend.backend.orden.dto.ProduccionDto;
import com.backend.backend.orden.dto.ProduccionFinalizadaDto;
import com.backend.backend.orden.entities.Produccion;
import com.backend.backend.orden.repositories.ProduccionRepository;
import com.backend.backend.orden.services.ProduccionService;
import com.backend.backend.security.dto.Mensaje;
import com.backend.backend.utils.PdfUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produccion")
@RequiredArgsConstructor
public class ProduccionController {

    private final ProduccionService produccionService;
    private final ProduccionRepository produccionRepository;
    private final PdfUtils pdfUtils;

    @PostMapping("/guardar")
    public ResponseEntity<Mensaje> guardarProduccion(@RequestBody ProduccionDto produccionDto) {
        Mensaje mensaje = produccionService.save(produccionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Produccion>> listarProducciones(Pageable pageable) {
        Page<Produccion> producciones = produccionService.listAll(pageable);
        return ResponseEntity.ok(producciones);
    }

    @PutMapping("/{id}/finalizar")
    public Mensaje finalizarProduccion(@PathVariable Long id, @RequestBody ProduccionFinalizadaDto produccionFinalizadaDto) {
        return produccionService.finalizarProduccion(id, produccionFinalizadaDto);
    }

    @GetMapping("/generar-pdf")
    public ResponseEntity<byte[]> generarPDF(@RequestParam(value = "estado", required = false) Integer estado,
                                             @RequestParam(value = "fechaEsperada", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaEsperada) throws Exception {
        List<?> lista1;
        lista1 = produccionService.listEstadoFechaEspera(estado, fechaEsperada);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista1);
        Map<String, Object> parameters = new HashMap<>();
        String rutaJRXML = "/reports/reporteOrdenes.jrxml";
        String nombreArchivo = "reporte de prueba.pdf";
        return pdfUtils.generarPDF(nombreArchivo, rutaJRXML, parameters, dataSource);
    }


}
