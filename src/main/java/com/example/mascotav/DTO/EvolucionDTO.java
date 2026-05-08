package com.example.mascotav.DTO;

import java.util.List;
import com.example.mascotav.model.Nivel;
import lombok.Data;
@Data
public class EvolucionDTO {
    private Integer id_evo;
    private String nom_evo;
    private Nivel id_nivel_FK;
    private List<String> nombres_tipos;
}
