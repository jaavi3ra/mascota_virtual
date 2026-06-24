package com.accion.accion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accion.accion.DTO.AccionDTO;
import com.accion.accion.model.Accion;
import com.accion.accion.repository.AccionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccionService {

    @Autowired
    private AccionRepository accionRepository;


    public List<AccionDTO> obtenerTodas() {
        log.info("Obteniendo todas las acciones");
        return accionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();

    }

    public Accion guardar(Accion accion) {
        log.info("Guardando nueva accion", accion.getNombreAccion());
        return accionRepository.save(accion);
    }

    public AccionDTO buscarPorId(Integer id) {
        log.info("Buscando accion por ID", id);

        Accion accion = accionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accion con ID " + id + " no encontrada."));
        return convertirADTO(accion);

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



