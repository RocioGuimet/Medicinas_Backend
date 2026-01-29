package com.medicina.repository;

import com.medicina.entity.Medicina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MedicinaRepository extends JpaRepository<Medicina, Long> {
    // Métodos CRUD básicos ya están incluidos
    List<Medicina> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT m FROM Medicina m JOIN m.sintomasQueAlivia s WHERE s.id = :sintomaId")
    List<Medicina> findBySintomaId(@Param("sintomaId") Long sintomaId);
}