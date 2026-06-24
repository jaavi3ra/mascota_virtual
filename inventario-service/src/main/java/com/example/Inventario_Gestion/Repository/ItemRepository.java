package com.example.Inventario_Gestion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Inventario_Gestion.Model.Item;

import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.tipoItem = :tipo")
    List<Item> buscarPorTipo(@Param("tipo") String tipo);
}