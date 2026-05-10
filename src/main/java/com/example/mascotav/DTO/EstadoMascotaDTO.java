
package com.example.mascotav.DTO;

import lombok.Data;

@Data
public class EstadoMascotaDTO {
    private Integer idEstadoMascota;
    private String nombreMascota;

    private Integer hambre;
    private Integer felicidad;
    private Integer energia;
    private Integer salud;

}
