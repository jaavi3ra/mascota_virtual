package com.mascota.mascota_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evolucion")
public class Evolucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_evo;

    @NotBlank(message = "El nombre de la evolucion es obligatorio")
    @Size(min = 4, max = 10, message = "El nombre debe tener al menos 4 caracteres")
    private String nom_evo;

    @NotNull
    @Column(name = "nivel_evo")
    private Integer nivel;

    @ManyToOne()
    @JoinColumn(name = "id_tipo_mascota_fk")
    private TipoMascota tipoMascota;
}
