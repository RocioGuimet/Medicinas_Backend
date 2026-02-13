package com.medicina.service;

import com.medicina.entity.Medicina;
import com.medicina.repository.MedicinaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicinaService {

    private final MedicinaRepository medicinaRepository;

    public MedicinaService(MedicinaRepository medicinaRepository) {
        this.medicinaRepository = medicinaRepository;
    }

    public List<Medicina> buscarPorMalestares(List<Long> sintomasIds) {
        // Busco Medicinas para AL MENOS UNO de los síntomas
        List<Medicina> todasLasCandidatas = medicinaRepository.findDistinctBySintomasQueAliviaIdIn(sintomasIds);

        // Intento encontrar una que cure TODOS los síntomas indicados
        List<Medicina> perfecta = todasLasCandidatas.stream()
                .filter(med -> {
                    List<Long> idsQueCuraEstaMedicina = med.getSintomasQueAlivia().stream()
                            .map(s -> s.getId())
                            .collect(Collectors.toList());

                    // ¿Contiene todos los que el usuario seleccionó?
                    return idsQueCuraEstaMedicina.containsAll(sintomasIds);
                })
                .collect(Collectors.toList());

        // Si no hay ninguna perfecta, devolvemos la lista general
        return !perfecta.isEmpty() ? perfecta : todasLasCandidatas;
    }
}