package com.example.mascotav.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "nivel")
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel")
    private Integer id_nivel;

    @NotNull
    @Column(name = "exp_req", nullable = false)
    private Integer exp_req;

    @OneToMany(mappedBy = "nivel")
    private List<Mascota> mascotas;

    @OneToMany(mappedBy = "nivel")
    private List<Evolucion> evoluciones;
}
