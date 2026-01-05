package com.medicina.repository;

import com.medicina.entity.Medicina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinaRepository extends JpaRepository<Medicina, Long> {
    // Métodos CRUD básicos ya están incluidos
}