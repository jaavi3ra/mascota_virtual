package com.example.mascotav.DTO;

import java.time.LocalDateTime;
import com.example.mascotav.model.TiendaItem;
import com.example.mascotav.model.Usuario;
import lombok.Data;
@Data
public class UsuarioTiendaItemDTO {
    private Integer id;
    private Usuario usuario;
    private TiendaItem itemdelatienda;
    private LocalDateTime cooldown;
}
