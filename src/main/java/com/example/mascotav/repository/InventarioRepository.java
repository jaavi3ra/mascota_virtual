package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer>{

}
