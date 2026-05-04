package com.example.mascotav.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    private int id_evo;
    
    @NotBlank(message = "El nombre de la evolucion es obligatorio")
    @Size(min = 4, max = 10, message = "El nombre debe tener al menos 4 caracteres")
    private String nom_evo;
  
    private int id_nivel_FK;
    private int id_mascota_FK;
}
