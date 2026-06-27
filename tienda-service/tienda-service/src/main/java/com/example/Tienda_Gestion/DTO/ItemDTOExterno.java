package com.example.Tienda_Gestion.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTOExterno {
    private Integer idItem;
    private String nombreItem;
    private String tipoItem;
}
