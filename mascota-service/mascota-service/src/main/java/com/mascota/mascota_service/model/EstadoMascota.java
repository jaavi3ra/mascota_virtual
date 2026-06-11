package com.mascota.mascota_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estadomascota")
public class EstadoMascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

   
    @NotNull(message = "El nivel de alimentación es obligatorio")
    @Min(value = 0, message = "La mascota ha muerto de hambre ")
    @Max(value = 200, message = "La mascota está completamente satisfecha ")
    @Column(name = "hambre", nullable = false)
    private Integer hambre ;

    
    @NotNull(message = "El nivel de felicidad es obligatorio")
    @Min(value = 0, message = "La mascota está en depresión total ")
    @Max(value = 200, message = "La mascota está sumamente feliz ")
    @Column(name = "felicidad", nullable = false)
    private Integer felicidad ;

   
    @NotNull(message = "El nivel de energía es obligatorio")
    @Min(value = 0, message = "La mascota está agotada, no puede más ")
    @Max(value = 200, message = "La mascota tiene energía máxima ")
    @Column(name = "energia", nullable = false)
    private Integer energia ;

    
    @NotNull(message = "El nivel de salud es obligatorio")
    @Min(value = 0, message = "La mascota ha fallecido por falta de salud ")
    @Max(value = 200, message = "Salud perfecta ")
    @Column(name = "salud", nullable = false)
    private Integer salud ;

    // Relaciones

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mascota", nullable = false, unique = true)
    private Mascota mascota;
}
