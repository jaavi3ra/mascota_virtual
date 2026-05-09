package com.example.mascotav.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Usuario_Tienda_Item;

@Repository
public interface Usuario_Tienda_ItemRepository  extends JpaRepository<Usuario_Tienda_Item, Integer>{
    
    //Optional<Usuario_Tienda_Item> findByCompraUsuarioAndItem(Integer userId, Integer itemId); // buscar una compra  por usuario e item

     //List<Usuario_Tienda_Item> findByComprasUsuario(Integer userId); //Obtener todas las compras del usuario

    //void deleteByCompraUsuarioAndItem(Integer userId, Integer itemId); // eliminar una compra  por usuario e item
}
