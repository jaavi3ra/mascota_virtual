package com.example.mascotav.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;

    @NotBlank(message = "El nombre del item es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del item debe tener entre 3 y 100 caracteres")
    @Column(name = "nom_item", length = 100, nullable = false)
    private String nombreItem;

    @NotBlank(message = "El tipo de item es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de item debe tener entre 3 y 50 caracteres")
    @Column(name = "tipo_item", length = 50, nullable = false)
    private String tipoItem;

    // Relaciones
    @ManyToOne()
    @JoinColumn(name = "id_accion_fk")
    private Accion accion;

    @OneToMany(mappedBy = "item")
    private List<Inventario> inventarios;

    @OneToMany(mappedBy = "item")
    private List<TiendaItem> tiendaItems;
}