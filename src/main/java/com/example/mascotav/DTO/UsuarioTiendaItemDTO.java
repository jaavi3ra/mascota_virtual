package com.example.mascotav.DTO;

import java.util.List;
import com.example.mascotav.model.Usuario;
import lombok.Data;
@Data
public class UsuarioTiendaItemDTO {
    private Integer id;
    private Usuario usuario;
    private List<String> itemsdelatienda;
}
