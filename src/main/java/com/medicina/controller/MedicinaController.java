package com.medicina.controller;

import com.medicina.entity.Medicina;
import com.medicina.entity.Sintoma;
import com.medicina.repository.MedicinaRepository;
import com.medicina.repository.SintomaRepository;
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
    @Autowired
    private SintomaRepository sintomaRepository;

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
    @GetMapping("/medicinas/{id}/sintomas")
    public ResponseEntity<?> obtenerSintomasDeMedicina(@PathVariable Long id) {
        try {
            Medicina medicina = medicinaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));

            List<Map<String, String>> response = medicina.getSintomasQueAlivia().stream()
                    .map(sintoma -> {
                        Map<String, String> sinMap = new HashMap<>();
                        sinMap.put("id", sintoma.getId().toString());
                        sinMap.put("nombre", sintoma.getNombre());
                        sinMap.put("descripcion", sintoma.getDescripcion());
                        return sinMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error obteniendo síntomas: ", e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/medicinas/sintoma/{sintomaId}")
    public ResponseEntity<?> obtenerMedicinasPorSintoma(@PathVariable Long sintomaId) {
        try {
            List<Medicina> medicinas = medicinaRepository.findBySintomaId(sintomaId);

            List<Map<String, String>> response = medicinas.stream()
                    .map(med -> {
                        Map<String, String> medMap = new HashMap<>();
                        medMap.put("id", med.getId().toString());
                        medMap.put("nombre", med.getNombre());
                        medMap.put("descripcion", med.getDescripcion());
                        medMap.put("modoUso", med.getModoUso());
                        return medMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/medicinas")
    public ResponseEntity<?> crearMedicina(@RequestBody Medicina medicina) {
        try {
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
    @PostMapping("/medicinas/{medicinaId}/sintomas/{sintomaId}")
    public ResponseEntity<?> agregarSintomaAMedicina(
            @PathVariable Long medicinaId,
            @PathVariable Long sintomaId) {

        try {
            Medicina medicina = medicinaRepository.findById(medicinaId)
                    .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));

            Sintoma sintoma = sintomaRepository.findById(sintomaId)
                    .orElseThrow(() -> new RuntimeException("Síntoma no encontrado"));

            if (!medicina.getSintomasQueAlivia().contains(sintoma)) {
                medicina.getSintomasQueAlivia().add(sintoma);
                medicinaRepository.save(medicina);
            }

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Síntoma agregado a medicina exitosamente");
            response.put("medicina", medicina.getNombre());
            response.put("sintoma", sintoma.getNombre());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            logger.error("Error agregando síntoma: ", e);
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/medicinas/{medicinaId}/sintomas")
    public ResponseEntity<?> agregarSintomasAMedicina(
            @PathVariable Long medicinaId,
            @RequestBody List<Long> sintomaIds) {

        try {
            Medicina medicina = medicinaRepository.findById(medicinaId)
                    .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));

            List<Sintoma> sintomas = sintomaRepository.findAllById(sintomaIds);

            int agregados = 0;
            for (Sintoma sintoma : sintomas) {
                if (!medicina.getSintomasQueAlivia().contains(sintoma)) {
                    medicina.getSintomasQueAlivia().add(sintoma);
                    agregados++;
                }
            }

            medicinaRepository.save(medicina);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", agregados + " síntomas agregados");
            response.put("medicina", medicina.getNombre());
            response.put("sintomasAgregados", agregados);
            response.put("totalSintomas", medicina.getSintomasQueAlivia().size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

