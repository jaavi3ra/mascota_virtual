package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascotav.model.Tienda_item;

@Repository
public interface Tienda_itemRepository extends JpaRepository<Tienda_item, Integer>{

}
