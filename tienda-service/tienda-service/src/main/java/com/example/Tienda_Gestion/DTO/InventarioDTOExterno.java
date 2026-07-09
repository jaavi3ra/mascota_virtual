package com.example.Tienda_Gestion.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventarioDTOExterno {
    private Integer id_inven;
    private Integer cantidad;
    private ItemDTOExterno item;
    private Integer idUserFk;
}
