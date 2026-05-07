package com.example.mascotav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mascotav.model.EstadoMascota;

public interface EstadoMascotaRepository extends JpaRepository<EstadoMascota, Integer> {

    List<EstadoMascota> findBySaludLessThan(Integer salud);

}
