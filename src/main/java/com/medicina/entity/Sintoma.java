package com.medicina.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
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

    @ManyToMany(mappedBy = "sintomasQueAlivia")
    private List<Medicina> medicinasRecomendadas = new ArrayList<>();
}