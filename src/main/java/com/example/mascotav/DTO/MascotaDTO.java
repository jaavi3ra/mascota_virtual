package com.example.mascotav.DTO;

import lombok.Data;

@Data
public class MascotaDTO {

    private Integer idMascota;
    private String nombre;
    private String tipoMascota;

    // Datos de Progreso y Evolución
    private Integer nivelActual;
    private Integer expActual;
    private Integer expSiguienteNivel;
    private String faseEvolucion;

    // Atributos de estado
    private Integer hambre;
    private Integer felicidad;
    private Integer energia;
    private Integer salud;
}
