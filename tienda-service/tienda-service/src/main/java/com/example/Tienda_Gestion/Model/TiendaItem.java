package com.example.Tienda_Gestion.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tiendaItem")

public class TiendaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTiendaItem;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_tienda_fk", nullable = false)
    private Tienda tienda;

    @NotNull
    @Column(name = "id_item_fk", nullable = false)
    private Integer idItemFk;
}
