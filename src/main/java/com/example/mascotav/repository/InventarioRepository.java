package com.example.mascotav.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Inventario;


@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer>{
    Optional<Inventario> findByUsuarioAndItem(Integer userId, Integer itemId);  // Buscar un item de un usuario || Optional es contenedor que puede o no tener un valor

     List<Inventario> findByUsuario(Integer userId); // Obtener todo el inventario de un usuario

     void deleteByUsuarioAndItem(Integer userId, Integer itemId); // elimnar un item de un usuario del inventario
}
