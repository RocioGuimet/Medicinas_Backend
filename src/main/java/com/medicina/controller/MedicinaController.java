package com.medicina.controller;

import com.medicina.entity.Medicina;
import com.medicina.repository.MedicinaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MedicinaController {

    private static final Logger logger = LoggerFactory.getLogger(MedicinaController.class);

    @Autowired
    private MedicinaRepository medicinaRepository;

    @GetMapping("/medicinas")
    public ResponseEntity<?> obtenerTodasMedicinas() {
        try {
            List<Medicina> medicinas = medicinaRepository.findAll();
            logger.info("✓ Medicinas obtenidas: " + medicinas.size());

            // SOLO datos básicos - SIN relaciones
            List<Map<String, String>> response = medicinas.stream().map(med -> {
                Map<String, String> medMap = new HashMap<>();
                medMap.put("id", med.getId().toString());
                medMap.put("nombre", med.getNombre());
                medMap.put("descripcion", med.getDescripcion());
                medMap.put("modoUso", med.getModoUso());
                return medMap;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage() +
                            "\nStack: " + e.getStackTrace()[0]);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Medicinas Naturales API");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/medicinas/count")
    public ResponseEntity<String> contarMedicinas() {
        long count = medicinaRepository.count();
        return ResponseEntity.ok("📊 Total medicinas en BD: " + count);
    }

    @GetMapping("/medicinas/buscar")
    public ResponseEntity<?> buscarMedicinas(@RequestParam(required = false) String q) {
        if (q == null || q.trim().isEmpty()) {
            // Si no hay query, devolver todas
            return obtenerTodasMedicinas();
        }

        List<Medicina> medicinas = medicinaRepository.findByNombreContainingIgnoreCase(q);

        List<Map<String, String>> response = medicinas.stream().map(med -> {
            Map<String, String> medMap = new HashMap<>();
            medMap.put("id", med.getId().toString());
            medMap.put("nombre", med.getNombre());
            medMap.put("descripcion", med.getDescripcion());
            return medMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    @PostMapping("/medicinas")
    public ResponseEntity<?> crearMedicina(@RequestBody Medicina medicina) {
        try {
            // Validación básica
            if (medicina.getNombre() == null || medicina.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"El nombre es obligatorio\"}");
            }

            Medicina saved = medicinaRepository.save(medicina);
            logger.info("✓ Medicina creada: " + saved.getNombre());

            Map<String, String> response = new HashMap<>();
            response.put("id", saved.getId().toString());
            response.put("nombre", saved.getNombre());
            response.put("descripcion", saved.getDescripcion());
            response.put("modoUso", saved.getModoUso());
            response.put("nombreCientifico", saved.getNombreCientifico());
            response.put("mensaje", "Medicina creada exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error creando medicina: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage() +
                            "\nStack: " + e.getStackTrace()[0]);
        }
    }
    @DeleteMapping("/medicinas/{id}")
    public ResponseEntity<?> eliminarMedicina(@PathVariable Long id) {
        try {
            if (!medicinaRepository.existsById(id)) {
                return ResponseEntity.status(404).body("{\"error\": \"La medicina con ID " + id + " no existe\"}");
            }

            medicinaRepository.deleteById(id);
            logger.info("🗑 Medicina eliminada - ID: " + id);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Medicina eliminada exitosamente");
            response.put("id", id.toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error eliminando medicina: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }
}

