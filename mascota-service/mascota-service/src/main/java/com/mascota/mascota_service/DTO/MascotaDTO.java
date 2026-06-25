package com.mascota.mascota_service.DTO;

import lombok.Data;

@Data
public class MascotaDTO {
    private Integer idMascota;
    private String nombre;
    private String tipoMascota;
    private Integer nivelActual;
    private Integer idUsuarioFk;
    private EstadoMascotaDTO estado;
}
