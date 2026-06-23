package com.example.Tienda_Gestion.DTO;

import lombok.Data;

@Data
public class InventarioDTOExterno {
    private Integer id_inven;
    private Integer cantidad;
    private Integer item;
    private Integer Usuario; // el inventario del usuario
}
