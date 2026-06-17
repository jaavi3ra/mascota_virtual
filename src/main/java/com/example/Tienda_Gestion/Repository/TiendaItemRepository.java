package com.example.Tienda_Gestion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascotav.model.TiendaItem;

@Repository
public interface TiendaItemRepository extends JpaRepository<TiendaItem, Integer> {

}
