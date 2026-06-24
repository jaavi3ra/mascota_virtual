package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mascota.mascota_service.DTO.EvolucionDTO;
import com.mascota.mascota_service.model.Evolucion;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.repository.EvolucionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
public class EvolucionService {
    @Autowired
    private EvolucionRepository evolucionRepository;
    public String verificarEvolucion(Mascota mascota){

        Evolucion evolucion = evolucionRepository
            .findByTipoMascota(mascota.getTipoMascota())
            .orElse(null);

         if(evolucion != null && mascota.getNivel().getNum_nivel() >= evolucion.getNivel()){

           return "Tu mascota "+mascota.getNombre()+" Evolucionó!";
        }
        return null;
    }

    public EvolucionDTO crearEvolucion(Evolucion evo){
            evolucionRepository.save(evo);
        return convertirADTO(evo);
        }

    private EvolucionDTO convertirADTO(Evolucion evo) { // metodo DTO sin uso
        EvolucionDTO evoDTO = new EvolucionDTO();
        evoDTO.setId_evo(evo.getId_evo());
        evoDTO.setNom_evo(evo.getNom_evo());
        if(evo.getNivel() != null){
           evoDTO.setNivelReq(evo.getNivel());
        }else{
            evoDTO.setNivelReq(0);

        if(evo.getTipoMascota().getId() != null){
            evoDTO.setNombre_tipo(evo.getTipoMascota().getNombreTipoMascota());
        } else {
            evoDTO.setNombre_tipo("Desconocido");
        }
        return evoDTO;
        }
        return null;

    }
}
