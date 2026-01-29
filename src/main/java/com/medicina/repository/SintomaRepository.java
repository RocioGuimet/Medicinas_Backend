package com.medicina.repository;

import com.medicina.entity.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SintomaRepository extends JpaRepository<Sintoma, Long> {

    @Query("SELECT s FROM Sintoma s JOIN s.medicinasRecomendadas m WHERE m.id = :medicinaId")
    List<Sintoma> findByMedicinaId(@Param("medicinaId") Long medicinaId);
}