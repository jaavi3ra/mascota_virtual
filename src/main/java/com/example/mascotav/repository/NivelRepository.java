package com.example.mascotav.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer>{
    

   
}
