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
   
    public NivelDTO crearNiveles(){
        Nivel nivel = null;
        for(int i = 1; i <= 10; i++){ //probar con 10 nivel max

            nivel = new Nivel();

            nivel.setNum_nivel(i);

            nivel.setExp_req(i * 1);
            nivelRepository.save(nivel);
           
        }
         return convertirADTO(nivel);
    }



     private NivelDTO convertirADTO (Nivel lvl){ // metodo DTO sin uso
        NivelDTO lvlDTO = new NivelDTO();
            lvlDTO.setId_nivel(lvl.getId_nivel());
            lvlDTO.setExp_req(lvl.getExp_req());
            
                return lvlDTO;

    }
}
