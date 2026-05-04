package com.example.mascotav.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_mascota")
public class TipoMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_mascota")
    private Integer id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(min = 10, max = 15, message = "El nombre debe tener al menos 4 caracteres")
    @Column(name = "nombreTipoMascota", length = 15, nullable = false)
    private String nombreTipoMascota;

    // Relaciones

}
