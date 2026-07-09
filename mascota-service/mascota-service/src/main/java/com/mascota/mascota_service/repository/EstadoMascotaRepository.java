package com.mascota.mascota_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mascota.mascota_service.model.EstadoMascota;

@Repository
public interface EstadoMascotaRepository extends JpaRepository<EstadoMascota, Integer> {

}
