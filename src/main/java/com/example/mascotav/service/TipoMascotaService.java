package com.example.mascotav.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public List<TipoMascotaDTO> creartipo(){
        List<String> nombres = Arrays.asList(
            "Dragon Paraplejico",
            "Lobo Chiguaga",
            "Slime Vencido",
            "Polvo de Fenix",
            "Tigre Ciego"
        );
        Collections.shuffle(nombres);

        TipoMascota tipos = null;
        for (String nombre : nombres) {
            tipos = new TipoMascota();
            tipos.setNombreTipoMascota(nombre);
            tipoMascotaRepository.save(tipos);
        }
        
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
