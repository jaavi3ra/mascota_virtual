package com.example.Tienda_Gestion.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTOExterno {
    private Integer idUsuario;
    private String nombreUser;
    private String fechaCreacion;

}
