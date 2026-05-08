package com.example.mascotav.DTO;

import java.util.List;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Usuario_Tienda_Item;

public class Tienda_itemDTO {
    private Integer id_tienda_item;
    private int cooldown_segundos;
    private Item id_item_FK;
    private List<String> comprasPorUsuario;

}
