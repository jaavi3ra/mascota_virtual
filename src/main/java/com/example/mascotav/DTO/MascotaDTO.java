package com.example.mascotav.DTO;

import lombok.Data;

@Data
public class MascotaDTO {

    private Integer idMascota;
    private String nombre;
    private String tipoMascota;
    private Integer nivelActual;

    private EstadoMascotaDTO estado;
}
