package com.example.Inventario_Gestion.DTO;

import lombok.Data;

@Data
public class MascotaDTOExterno {

    private Integer idMascota;
    private EstadoMascotaDTOExterno estado;
    private String nombre;
    private Integer nivelActual;
    private Integer idUsuarioFk;
}
