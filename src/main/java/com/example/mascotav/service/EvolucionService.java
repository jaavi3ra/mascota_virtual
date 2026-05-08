package com.example.mascotav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.EvolucionDTO;
import com.example.mascotav.model.Evolucion;
import com.example.mascotav.repository.EvolucionRepository;

@Service
public class EvolucionService {
    @Autowired
    private EvolucionRepository evolucionRepository;

    public Evolucion crearEvolucion(Evolucion evo){
            return evolucionRepository.save(evo);
        }
     
    public Evolucion editarEvolucion(Integer id,Evolucion evo){
          Evolucion evoeditado = evolucionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Esa Evolucion no existe. Prueba con otro."));
        if(evoeditado.getNom_evo() != null){
            evo.setNom_evo(evoeditado.getNom_evo());
        }
        if(evoeditado.getId_nivel_FK() != null){
            evo.setId_nivel_FK(evoeditado.getId_nivel_FK());
        }
        if(evoeditado.getId_tipoMascota_FK() != null){
            evo.setId_tipoMascota_FK(evoeditado.getId_tipoMascota_FK());
        }
        return evolucionRepository.save(evoeditado);
    }
    public String borrarEvolucion(Integer id){
         Evolucion evo = evolucionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Esa Evolucion no existe. Prueba con otro."));
        if(evo.getId_evo() != null){      
            evolucionRepository.deleteById(evo.getId_evo());  
            return "Evolución eliminada.";
        }       
        return "No se pudo eliminar";
    }



     private EvolucionDTO convertirADTO (Evolucion evo){
        EvolucionDTO evoDTO = new EvolucionDTO();
        evoDTO.setId_evo(evo.getId_evo());
        evoDTO.setNom_evo(evo.getNom_evo());
        if(evo.getId_nivel_FK() != null){
           evoDTO.setNivelReq(evo.getId_nivel_FK().getId_nivel());
        }else{
            evoDTO.setNivelReq(0);

        if(evo.getId_tipoMascota_FK() != null){
            evoDTO.setNombre_tipo(evo.getId_tipoMascota_FK().getNombreTipoMascota());
        } else {
            evoDTO.setNombre_tipo("Desconocido");
        }
        return evoDTO;
        }

    }
    
}
