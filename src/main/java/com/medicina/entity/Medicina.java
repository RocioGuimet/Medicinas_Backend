package com.medicina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "medicinas")
@NoArgsConstructor
@AllArgsConstructor
public class Medicina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private String modoUso;
    private String nombreCientifico;

    @ManyToMany
    @JoinTable(
            name = "medicina_sintoma",
            joinColumns = @JoinColumn(name = "medicina_id"),
            inverseJoinColumns = @JoinColumn(name = "sintoma_id")
    )
    private List<Sintoma> sintomasQueAlivia = new ArrayList<>();
}