package com.example.mascotav.DTO;

import java.util.List;
import lombok.Data;

@Data
public class AccionDTO {

    private Integer IdAccion;
    private String nombreAccion;
    private Integer afectaFelicidad;
    private Integer afectaEnergia;
    private Integer afectaSalud;
    private Integer afectaHambre;
    private Integer afectaExpBase;
    private List<String> nombres_acciones;
}
