package com.medicina.controller;

import com.medicina.entity.Medicina;
import com.medicina.repository.MedicinaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicinas")
public class MedicinaController {

    private static final Logger logger = LoggerFactory.getLogger(MedicinaController.class);

    @Autowired
    private MedicinaRepository medicinaRepository;

    @GetMapping
    public List<Medicina> obtenerTodasMedicinas() {
        List<Medicina> medicinas = medicinaRepository.findAll();
        logger.info("Medicinas obtenidas: " + medicinas.size());
        return medicinas;
    }
}