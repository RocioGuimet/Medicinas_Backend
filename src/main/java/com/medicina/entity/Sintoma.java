package com.medicina.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "sintomas")
public class Sintoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    // LADO INVERSO de la relación
    @ManyToMany(mappedBy = "sintomasQueAlivia")
    private List<Medicina> medicinasRecomendadas;
}