package com.example.mascotav.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
    @Column(name = "nombre_user", length = 15, nullable = false)
    private String nombreUsuario;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // --- RELACIÓN ---

    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private List<Mascota> mascotas;
}
