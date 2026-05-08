package com.example.mascotav.DTO;

import java.util.List;

import com.example.mascotav.model.Nivel;
import com.example.mascotav.model.TipoMascota;

public class EvolucionDTO {
    private Integer id_evo;
    private String nom_evo;
    private Nivel id_nivel_FK;
    private List<String> nombres_tipos;
}
