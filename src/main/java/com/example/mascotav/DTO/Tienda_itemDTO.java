package com.example.mascotav.DTO;

import java.util.List;
import com.example.mascotav.model.Item;
import lombok.Data;
@Data
public class Tienda_itemDTO {
    private Integer id_tienda_item;
    private int cooldown_segundos;
    private Item id_item_FK;
    private List<String> comprasPorUsuario;

}
