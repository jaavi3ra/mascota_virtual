package com.example.mascotav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.NivelDTO;
import com.example.mascotav.model.Nivel;
import com.example.mascotav.repository.NivelRepository;

@Service
public class NivelService {
    @Autowired
    private NivelRepository nivelRepository;
   
    public Nivel iniciarNivel(){
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
