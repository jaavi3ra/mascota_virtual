package com.example.mascotav.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.UsuarioTiendaItem;

@Repository
public interface UsuarioTiendaItemRepository  extends JpaRepository<UsuarioTiendaItem, Integer>{
    
    //Optional<UsuarioTiendaItem> findByCompraUsuarioAndItem(Integer userId, Integer itemId); // buscar una compra  por usuario e item

     //List<UsuarioTiendaItem> findByComprasUsuario(Integer userId); //Obtener todas las compras del usuario

    //void deleteByCompraUsuarioAndItem(Integer userId, Integer itemId); // eliminar una compra  por usuario e item
}
