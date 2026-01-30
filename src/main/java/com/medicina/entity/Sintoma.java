package com.medicina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "sintomas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sintoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
    private String descripcion;

    @ManyToMany(mappedBy = "sintomasQueAlivia")
    private Set<Medicina> medicinasRecomendadas = new HashSet<>();
}