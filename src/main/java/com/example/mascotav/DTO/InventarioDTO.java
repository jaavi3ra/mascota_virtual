package com.example.mascotav.DTO;

import com.example.mascotav.model.Item;
import lombok.Data;
@Data
public class InventarioDTO {
     private Integer id_inven;
     private Integer cantidad;
     private Item id_item_FK;
     private Integer idinventarioUsuario; // el inventario del usuario
}
