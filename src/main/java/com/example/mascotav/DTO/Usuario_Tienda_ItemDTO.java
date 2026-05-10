package com.example.mascotav.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mascotav.model.Tienda_item;
import com.example.mascotav.model.Usuario;
import lombok.Data;
@Data
public class Usuario_Tienda_ItemDTO {
    private Integer id;
    private Usuario usuario;
    private Tienda_item itemdelatienda;
    private LocalDateTime cooldown;
}
