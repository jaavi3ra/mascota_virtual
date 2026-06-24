package com.example.Inventario_Gestion.DTO;

import lombok.Data;

@Data
public class InventarioDTO {
    private Integer id_inven;
    private Integer cantidad;
    private Integer item;
    private Integer Usuario; // el inventario del usuario
}