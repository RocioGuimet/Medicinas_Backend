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
public class SintomaController {

    private static final Logger logger = LoggerFactory.getLogger(SintomaController.class);

    @Autowired
    private SintomaRepository sintomaRepository;
    @Autowired
    private MedicinaRepository medicinaRepository;

    @GetMapping("/sintomas")
    public ResponseEntity<?> obtenerTodosSintomas() {
        try {
            List<Sintoma> sintomas = sintomaRepository.findAll();
            logger.info("✓ Síntomas obtenidos: " + sintomas.size());

            // Mantener formato similar a medicinas
            List<Map<String, String>> response = sintomas.stream().map(sin -> {
                Map<String, String> sinMap = new HashMap<>();
                sinMap.put("id", sin.getId().toString());
                sinMap.put("nombre", sin.getNombre());
                sinMap.put("descripcion", sin.getDescripcion());
                return sinMap;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage() +
                            "\nStack: " + e.getStackTrace()[0]);
        }
    }

    @GetMapping("/sintomas/{id}/medicinas")
    public ResponseEntity<?> obtenerMedicinasDeSintoma(@PathVariable Long id) {
        try {
            Sintoma sintoma = sintomaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Síntoma no encontrado"));

            List<Map<String, String>> response = sintoma.getMedicinasRecomendadas().stream()
                    .map(medicina -> {
                        Map<String, String> medMap = new HashMap<>();
                        medMap.put("id", medicina.getId().toString());
                        medMap.put("nombre", medicina.getNombre());
                        medMap.put("descripcion", medicina.getDescripcion());
                        medMap.put("modoUso", medicina.getModoUso());
                        return medMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error obteniendo medicinas: ", e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sintomas/medicina/{medicinaId}")
    public ResponseEntity<?> obtenerSintomasPorMedicina(@PathVariable Long medicinaId) {
        try {
            List<Sintoma> sintomas = sintomaRepository.findByMedicinaId(medicinaId);

            List<Map<String, String>> response = sintomas.stream()
                    .map(sin -> {
                        Map<String, String> sinMap = new HashMap<>();
                        sinMap.put("id", sin.getId().toString());
                        sinMap.put("nombre", sin.getNombre());
                        sinMap.put("descripcion", sin.getDescripcion());
                        return sinMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/sintomas")
    public ResponseEntity<?> crearSintoma(@RequestBody Sintoma sintoma) {
        try {
            if (sintoma.getNombre() == null || sintoma.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"El nombre es obligatorio\"}");
            }

            Sintoma saved = sintomaRepository.save(sintoma);
            logger.info("✓ Síntoma creado: " + saved.getNombre());

            Map<String, String> response = new HashMap<>();
            response.put("id", saved.getId().toString());
            response.put("nombre", saved.getNombre());
            response.put("descripcion", saved.getDescripcion());
            response.put("mensaje", "Síntoma creado exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error creando síntoma: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage() +
                            "\nStack: " + e.getStackTrace()[0]);
        }
    }
    @PostMapping("/sintomas/{sintomaId}/medicinas/{medicinaId}")
    public ResponseEntity<?> agregarMedicinaASintoma(
            @PathVariable Long sintomaId,
            @PathVariable Long medicinaId) {

        try {

            Sintoma sintoma = sintomaRepository.findById(sintomaId)
                    .orElseThrow(() -> new RuntimeException("Síntoma no encontrado"));

            Medicina medicina = medicinaRepository.findById(medicinaId)
                    .orElseThrow(() -> new RuntimeException("Medicina no encontrada"));

            if (!sintoma.getMedicinasRecomendadas().contains(medicina)) {
                sintoma.getMedicinasRecomendadas().add(medicina);
                sintomaRepository.save(sintoma);
            }

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Medicina agregada al síntoma exitosamente");
            response.put("sintoma", sintoma.getNombre());
            response.put("medicina", medicina.getNombre());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            logger.error("Error agregando medicina: ", e);
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}