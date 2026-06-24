package com.accion.accion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.accion.accion.model.Accion;

@Repository
public interface AccionRepository extends JpaRepository<Accion, Integer> {

}
