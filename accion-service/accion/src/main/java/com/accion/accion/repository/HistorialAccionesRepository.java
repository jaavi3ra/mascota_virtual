package com.accion.accion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accion.accion.model.HistorialAcciones;

@Repository
public interface HistorialAccionesRepository extends JpaRepository<HistorialAcciones, Integer> {

}

