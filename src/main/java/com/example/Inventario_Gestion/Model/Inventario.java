package com.example.Inventario_Gestion.Model;

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

@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inven;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @JoinColumn(name = "id_user_fk")
    private Integer idUserFk;

    @ManyToOne()
    @JoinColumn(name = "id_item_fk")
    private Item item;

}