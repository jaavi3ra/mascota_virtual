package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Item;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.tipoItem = :tipo")
    List<Item> buscarPorTipo(@Param("tipo") String tipo);
}