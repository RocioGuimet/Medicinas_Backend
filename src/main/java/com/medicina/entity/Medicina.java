package com.medicina.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "medicinas")
public class Medicina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private String modoUso;
    private String nombreCientifico;

    // LADO PROPIETARIO de la relación
    @ManyToMany
    @JoinTable(
            name = "medicina_sintoma",
            joinColumns = @JoinColumn(name = "medicina_id"),
            inverseJoinColumns = @JoinColumn(name = "sintoma_id")
    )
    private List<Sintoma> sintomasQueAlivia;
}