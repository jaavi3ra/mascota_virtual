package com.example.mascotav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mascotav.model.Mascota;

import feign.Param;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

    Mascota findTopByOrderByNivelDesc();

    // Busca coincidencia EXACTA (debe ser igual)
    // Ej: "Triste" → solo encuentra "Triste"
    List<Mascota> findByEstadoNombre(String nombre);

    // Busca coincidencia PARCIAL (contiene el texto) --Containing
    // Ej: "Tri" → encuentra "Triste", "Tristeza"
    List<Mascota> findByEstadoNombreContaining(String nombre);

    @Query("SELECT m FROM Mascota m WHERE m.nivel >= :nivelMinimo")
    List<Mascota> buscarMascotasFuertes(@Param("nivelMinimo") Integer nivelMinimo);
}
