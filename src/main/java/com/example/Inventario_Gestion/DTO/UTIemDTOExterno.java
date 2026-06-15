package com.example.Inventario_Gestion.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data

class UTIemDTOExterno {
    private Integer id;
    private Integer idUsuario;
    private Integer idTiendaItem;
    private LocalDateTime cooldown;
}
