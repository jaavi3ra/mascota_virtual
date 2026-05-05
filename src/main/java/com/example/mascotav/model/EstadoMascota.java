package com.example.mascotav.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estado_mascota")
public class EstadoMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer id;

    @Builder.Default
    @NotNull(message = "El nivel de alimentación es obligatorio")
    @Min(value = 0, message = "La mascota ha muerto de hambre ")
    @Max(value = 100, message = "La mascota está completamente satisfecha ")
    @Column(name = "hambre", nullable = false)
    private Integer hambre = 50;

    @Builder.Default
    @NotNull(message = "El nivel de felicidad es obligatorio")
    @Min(value = 0, message = "La mascota está en depresión total ")
    @Max(value = 100, message = "La mascota está sumamente feliz ")
    @Column(name = "felicidad", nullable = false)
    private int felicidad = 100;

    @Builder.Default
    @NotNull(message = "El nivel de energía es obligatorio")
    @Min(value = 0, message = "La mascota está agotada, no puede más ")
    @Max(value = 100, message = "La mascota tiene energía máxima ")
    @Column(name = "energia", nullable = false)
    private int energia = 100;

    @Builder.Default
    @NotNull(message = "El nivel de salud es obligatorio")
    @Min(value = 0, message = "La mascota ha fallecido por falta de salud ")
    @Max(value = 100, message = "Salud perfecta ")
    @Column(name = "salud", nullable = false)
    private int salud = 100;

    // Relaciones

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mascota", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Mascota mascota;

}
