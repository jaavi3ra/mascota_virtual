package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascotav.model.Evolucion;

@Repository
public interface EvolucionRepository extends JpaRepository<Evolucion, Integer>{

}
