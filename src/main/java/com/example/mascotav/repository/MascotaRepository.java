package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mascotav.model.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

}
