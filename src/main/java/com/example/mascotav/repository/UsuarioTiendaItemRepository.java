package com.example.mascotav.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.TiendaItem;
import com.example.mascotav.model.Usuario;
import com.example.mascotav.model.UsuarioTiendaItem;

@Repository
public interface UsuarioTiendaItemRepository  extends JpaRepository<UsuarioTiendaItem, Integer>{
    
    Optional<UsuarioTiendaItem> findByUsuarioAndTiendaItem(Usuario user, TiendaItem tiendaitem); // buscar una compra  por usuario e item

     //List<UsuarioTiendaItem> findByComprasUsuario(Integer userId); //Obtener todas las compras del usuario

    //void deleteByCompraUsuarioAndItem(Integer userId, Integer itemId); // eliminar una compra  por usuario e item
}
