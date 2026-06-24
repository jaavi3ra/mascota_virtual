package com.mascota.mascota_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mascota.mascota_service.model.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer>{

}
