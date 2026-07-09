package com.mascotav.usuario_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 5, max = 15, message = "El nombre debe tener entre 5 y 15 caracteres")
    @Column(name = "nombre_user", nullable = false)
    private String nombreUsuario;

    @Column(name = "fecha_creacion", updatable = false)
    private String fechaCreacion;

}
