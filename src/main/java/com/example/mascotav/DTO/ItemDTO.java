package com.example.mascotav.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ItemDTO {

    private Integer idItem;
    private String nombreItem;
    private String tipoItem;
    private List<String> items_comprados;
    private List<String> items_nombres;
}
