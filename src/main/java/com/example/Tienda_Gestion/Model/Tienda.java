package com.example.Tienda_Gestion.Model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tienda")

public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tienda")
    private Integer idTienda;

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre de la tienda debe tener entre 3 y 100 caracteres")
    @Column(name = "nom_tienda", length = 100, nullable = false)
    private String nombreTienda;

    // Relaciones
    @OneToMany(mappedBy = "tienda")
    private List<TiendaItem> tienda_items;
}
