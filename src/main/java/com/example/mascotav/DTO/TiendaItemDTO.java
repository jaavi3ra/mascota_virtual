package com.example.mascotav.DTO;

import java.util.List;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Tienda;

import lombok.Data;
@Data
public class TiendaItemDTO {
    private Integer id_tienda_item;
    private int cooldown_segundos;
    private Item id_item_FK;
    private Tienda id_tienda_FK;

}
