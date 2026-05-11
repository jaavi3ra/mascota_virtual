package com.example.mascotav.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mascotav.model.Evolucion;
import com.example.mascotav.model.TipoMascota;

@Repository
public interface EvolucionRepository extends JpaRepository<Evolucion, Integer>{
    Optional<Evolucion> findByTipoMascota(TipoMascota tipoMascota);
}
