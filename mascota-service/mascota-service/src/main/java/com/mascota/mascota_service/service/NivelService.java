package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascota.mascota_service.DTO.NivelDTO;
import com.mascota.mascota_service.model.Nivel;
import com.mascota.mascota_service.repository.NivelRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NivelService {
    @Autowired
    private NivelRepository nivelRepository;
   
    public Nivel iniciarNivel(){
        log.info("iniciando nivel para mascota...");
        Nivel nivel = new Nivel();   
            nivel.setNum_nivel(1);
            nivel.setExp_req(10);
            nivelRepository.save(nivel);
        
            convertirADTO(nivel);
         return nivel;
    }

     private NivelDTO convertirADTO (Nivel lvl){ // metodo DTO sin uso
        NivelDTO lvlDTO = new NivelDTO();
            lvlDTO.setId_nivel(lvl.getId_nivel());
            lvlDTO.setExp_req(lvl.getExp_req());
            
                return lvlDTO;

    }
}
