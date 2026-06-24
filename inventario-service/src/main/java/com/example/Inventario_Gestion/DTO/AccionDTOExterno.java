package com.example.Inventario_Gestion.DTO;

import lombok.Data;

@Data
public class AccionDTOExterno {
    private Integer idAccion;
    private String nombreAccion;
    private Integer afectaFelicidad;
    private Integer afectaEnergia;
    private Integer afectaSalud;
    private Integer afectaHambre;
    private Integer afectaExpBase;
}
