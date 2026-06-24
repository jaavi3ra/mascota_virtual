package com.accion.accion.DTO;

import lombok.Data;

@Data
public class ItemDTOExterno {
    private Integer idItem;
    private String nombreItem;
    private String tipoItem;
    private Integer idAccionFk;

}
