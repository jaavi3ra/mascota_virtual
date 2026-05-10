package com.example.mascotav.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name= "usuariotiendaitem",
     uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "id_tienda_item"}))
public class UsuarioTiendaItem {
    //tabla intermedia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ultimaCompra")
    private LocalDateTime ultimaCompra;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_tienda_item_fk")
    private TiendaItem tiendaItem;
}
