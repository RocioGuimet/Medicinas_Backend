package com.medicina.controller;

import com.medicina.entity.Sintoma;
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
    @DeleteMapping("/sintomas/{id}")
    public ResponseEntity<?> eliminarSintoma(@PathVariable Long id) {
        try {
            if (!sintomaRepository.existsById(id)) {
                return ResponseEntity.status(404).body("{\"error\": \"El síntoma con ID " + id + " no existe\"}");
            }

            sintomaRepository.deleteById(id);
            logger.info("🗑 Síntoma eliminado - ID: " + id);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Síntoma eliminado exitosamente");
            response.put("id", id.toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("✗ Error eliminando síntoma: ", e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/sintomas/{id}")
    public ResponseEntity<?> actualizarSintoma(@PathVariable Long id, @RequestBody Sintoma sintomaDetalles) {
        try {
            return sintomaRepository.findById(id).map(sintoma -> {
                sintoma.setNombre(sintomaDetalles.getNombre());
                sintoma.setDescripcion(sintomaDetalles.getDescripcion());

                Sintoma actualizado = sintomaRepository.save(sintoma);
                logger.info("修 Síntoma actualizado - ID: " + id);

                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Síntoma actualizado exitosamente");
                response.put("data", actualizado);
                return ResponseEntity.ok((Object) response);
            }).orElse(ResponseEntity.status(404).body("{\"error\": \"No se encontró el síntoma con ID " + id + "\"}"));

        } catch (Exception e) {
            logger.error("✗ Error al actualizar síntoma: ", e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}