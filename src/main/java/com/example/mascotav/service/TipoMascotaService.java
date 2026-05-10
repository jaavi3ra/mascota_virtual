package com.example.mascotav.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascotav.DTO.TipoMascotaDTO;
import com.example.mascotav.model.TipoMascota;
import com.example.mascotav.repository.TipoMascotaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoMascotaService {

    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    public List<TipoMascotaDTO> obtenerTodos() {
        return tipoMascotaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    private TipoMascotaDTO convertirADTO(TipoMascota tipo) {

        TipoMascotaDTO tipDTO = new TipoMascotaDTO();
        tipDTO.setIdTipoMascota(tipo.getId());
        tipDTO.setNomTipo(tipo.getNombreTipoMascota());

        return tipDTO;
    }

}
