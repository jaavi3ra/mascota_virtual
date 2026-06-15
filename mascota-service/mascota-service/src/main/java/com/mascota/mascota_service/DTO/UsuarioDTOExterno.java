package com.mascota.mascota_service.DTO;

import lombok.Data;

@Data
public class UsuarioDTOExterno {
    private Integer idUsuario;
    private String nombreUser;
    private String fechaCreacion;

    private Integer cantidadMascotas;
    private Integer totalItemsInventario;
}
