package com.example.Tienda_Gestion.DTO;

import com.example.Tienda_Gestion.Model.Tienda;

import lombok.Data;

@Data
public class TiendaItemDTO {
    private Integer idTiendaItem;
    private Integer id_item_FK;
    private Tienda id_tienda_FK;
}
