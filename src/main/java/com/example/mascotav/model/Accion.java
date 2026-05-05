package com.example.mascotav.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "accion")
public class Accion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accion")
    private int idAccion;
    
    @NotBlank(message = "El nombre de la accion es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre_accion", length = 100, nullable = false)
    private String nombreAccion;

    @NotNull(message = "Debe indicar como afecta la felicidad")
    @Column(name = "afecta_felicidad", nullable = false)
    private int afectaFelicidad;

    @NotNull(message = "Debe indicar como afecta la energia")
    @Column(name = "afecta_energia", nullable = false)
    private int afectaEnergia;

    @NotNull(message = "Debe indicar cómo afecta la salud")
    @Column(name = "afecta_salud", nullable = false)
    private int afectaSalud;

    @NotNull(message = "Debe indicar cómo afecta el hambre")
    @Column(name = "afecta_hambre", nullable = false)
    private int afectaHambre;

    @NotNull(message = "Debe indicar la experiencia base otorgada")
    @Column(name = "afecta_exp_base", nullable = false)
    private int afectaExpBase;
}
