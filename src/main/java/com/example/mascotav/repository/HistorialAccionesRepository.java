package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.HistorialAcciones;

@Repository
public interface HistorialAccionesRepository extends JpaRepository<HistorialAcciones, Integer> {

}
