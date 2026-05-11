package com.example.mascotav.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String nombreUser;
    private String fechaCreacion;

    private Integer cantidadMascotas;
    private Integer totalItemsInventario;

}
