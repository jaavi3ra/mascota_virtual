package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Tienda;
import java.util.List;


@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    //boolean existByNombreTienda(List<Tienda> findByNombreTienda(String nombreTienda));
    boolean existsByNombreTienda(String nombreTienda);
}