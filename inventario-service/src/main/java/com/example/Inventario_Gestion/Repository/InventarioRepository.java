package com.example.Inventario_Gestion.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.Inventario_Gestion.Model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    @Query("SELECT i FROM Inventario i WHERE i.idUserFk = :userId AND i.item.idItem = :itemId")
    Optional<Inventario> findByUsuarioAndItem(@Param("userId") Integer userId, @Param("itemId") Integer itemId);
    // Buscar un item de un usuario || Optional es contenedor que puede o no tener
    // un valor

    @Query("SELECT i FROM Inventario i WHERE i.idUserFk = :userId")
    List<Inventario> findInventbyUsuario(Integer userId); // Obtener todo el inventario de un usuario

    // void deleteByUsuarioAndItem(Integer userId, Integer itemId); // elimnar un
    // item de un usuario del inventario
}