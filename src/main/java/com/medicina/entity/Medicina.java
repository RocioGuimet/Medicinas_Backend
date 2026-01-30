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
@Table(name = "medicinas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private Set<Sintoma> sintomasQueAlivia = new HashSet<>();
}