package com.example.mascotav.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_inven;

    @ManyToOne
    @JoinColumn(name = "id_item") //falta tabla item
    private int id_item_FK;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota id_mascosta_FK;
}
