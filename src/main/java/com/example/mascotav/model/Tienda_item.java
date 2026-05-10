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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "tienda_item")
public class Tienda_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tienda_item;

    @NotNull
    @Column(name = "cooldown", nullable = false)
    private int cooldown_segundos;

    @ManyToOne()
    @JoinColumn(name = "id_tienda_fk")
    private Tienda tienda;

    @ManyToOne()
    @JoinColumn(name = "id_item_fk")
    private Item item;

    @OneToMany(mappedBy = "tienda_item")
    private List<Usuario_Tienda_Item> reclamaciones;
}
