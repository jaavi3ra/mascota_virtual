package com.mascota.mascota_service.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascota.mascota_service.DTO.TipoMascotaDTO;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.TipoMascotaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TipoMascotaService {
    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    public List<TipoMascotaDTO> findAll() {
        List<TipoMascotaDTO> listaDTOs = new ArrayList<>();
        for (TipoMascota tipo : tipoMascotaRepository.findAll()) {
            listaDTOs.add(convertirADTO(tipo));
        }
        return listaDTOs;
    }
    
    public TipoMascotaDTO creartipo(){
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
        
        log.info("Tipos de Mascotas creadas.");
        return convertirADTO(tipos);
        
    }

    private TipoMascotaDTO convertirADTO(TipoMascota tipo) {

        TipoMascotaDTO tipDTO = new TipoMascotaDTO();
        tipDTO.setIdTipoMascota(tipo.getId());
        tipDTO.setNomTipo(tipo.getNombreTipoMascota());

        return tipDTO;
    }
}
