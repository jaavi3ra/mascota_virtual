package com.mascotav.usuario_service.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String nombreUser;
    private String fechaCreacion;

    //borrar o crear metodo
    private Integer cantidadMascotas;
    private Integer totalItemsInventario;
}
