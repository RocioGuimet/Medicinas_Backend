package com.medicina.repository;

import com.medicina.entity.Medicina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicinaRepository extends JpaRepository<Medicina, Long> {
    List<Medicina> findByNombreContainingIgnoreCase(String nombre);
}