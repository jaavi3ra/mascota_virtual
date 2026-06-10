package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.AccionDTO;
import com.example.mascotav.model.Accion;
import com.example.mascotav.repository.AccionRepository;

@Service
public class AccionService {
    @Autowired
    private AccionRepository accionRepository;

    public List<AccionDTO> obtenerTodas() {
        return accionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Accion guardar(Accion accion) {
        return accionRepository.save(accion);
    }


    private AccionDTO convertirADTO(Accion accion) {
        AccionDTO accDTO = new AccionDTO();
        accDTO.setIdAccion(accion.getIdAccion());
        accDTO.setNombreAccion(accion.getNombreAccion());
        accDTO.setAfectaFelicidad(accion.getAfectaFelicidad());
        accDTO.setAfectaEnergia(accion.getAfectaEnergia());
        accDTO.setAfectaSalud(accion.getAfectaSalud());
        accDTO.setAfectaHambre(accion.getAfectaHambre());
        accDTO.setAfectaExpBase(accion.getAfectaExpBase());

        List<String> nombresAccion = new ArrayList<>();
        nombresAccion.add(accion.getNombreAccion());
        accDTO.setNombres_acciones(nombresAccion);

        return accDTO;
    }
}