
package com.example.mascotav.DTO;

import lombok.Data;

@Data
public class EstadoMascotaDTO {
    private Integer idMascota;
    private String nombreMascota;

    private Integer hambre;
    private Integer felicidad;
    private Integer energia;
    private Integer salud;

}
