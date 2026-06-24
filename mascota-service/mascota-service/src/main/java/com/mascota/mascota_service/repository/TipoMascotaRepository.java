package com.mascota.mascota_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mascota.mascota_service.model.TipoMascota;

@Repository
public interface TipoMascotaRepository extends JpaRepository<TipoMascota, Integer>{

}
