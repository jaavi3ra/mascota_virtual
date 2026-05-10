package com.example.mascotav.DTO;

<<<<<<< HEAD
import java.util.List;
=======
import java.time.LocalDateTime;
import java.util.List;

import com.example.mascotav.model.TiendaItem;
>>>>>>> feature/javi3ra
import com.example.mascotav.model.Usuario;
import lombok.Data;
@Data
public class UsuarioTiendaItemDTO {
    private Integer id;
    private Usuario usuario;
<<<<<<< HEAD
    private List<String> itemsdelatienda;
=======
    private TiendaItem itemdelatienda;
    private LocalDateTime cooldown;
>>>>>>> feature/javi3ra
}
