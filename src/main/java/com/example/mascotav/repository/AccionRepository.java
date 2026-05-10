package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Accion;

@Repository
public interface AccionRepository extends JpaRepository<Accion, Integer> {

}
