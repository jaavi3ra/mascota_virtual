package com.example.Tienda_Gestion.DTO;

import java.util.List;
import lombok.Data;

@Data
public class TiendaDTO {

    private Integer idTienda;
    private String nombreTienda;
    private List<String> itemsDelatienda;
}
