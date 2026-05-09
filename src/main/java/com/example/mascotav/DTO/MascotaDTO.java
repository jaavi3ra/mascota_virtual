package com.example.mascotav.DTO;

import lombok.Data;

@Data
public class MascotaDTO {

    private Integer idMascota;
    private String nombre;
    private String tipoMascota;
    private Integer nivelActual;

    // Atributos de estado
    private Integer hambre;
    private Integer felicidad;
    private Integer energia;
    private Integer salud;
}
