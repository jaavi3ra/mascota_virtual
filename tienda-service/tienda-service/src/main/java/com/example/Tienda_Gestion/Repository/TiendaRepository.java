package com.example.Tienda_Gestion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Tienda_Gestion.Model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    // boolean existByNombreTienda(List<Tienda> findByNombreTienda(String
    // nombreTienda));
    boolean existsByNombreTienda(String nombreTienda);
}