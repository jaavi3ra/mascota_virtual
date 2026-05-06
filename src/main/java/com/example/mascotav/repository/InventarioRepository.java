package com.example.mascotav.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Tienda_item;
import com.example.mascotav.model.Usuario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer>{
    Optional<Inventario> findByUsuarioAndItem(Usuario usuario, Tienda_item item);  // Buscar un item de un usuario || Optional es contenedor que puede o no tener un valor

     List<Inventario> findByUsuario(Usuario usuario); // Obtener todo el inventario de un usuario

     void deleteByUsuarioAndItem(Usuario usuario, Tienda_item item); // elimnar un item de un usuario del inventario
}
