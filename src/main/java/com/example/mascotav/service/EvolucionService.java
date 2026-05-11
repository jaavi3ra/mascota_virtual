package com.example.mascotav.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.EvolucionDTO;
import com.example.mascotav.model.Evolucion;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.EvolucionRepository;

@Service
public class EvolucionService {
    @Autowired
    private EvolucionRepository evolucionRepository;

    public List<EvolucionDTO> obtenerTodas() {
        return evolucionRepository.findAll().stream()
                .map(this::convertirADTO) // Transmutamos cada party
                .toList();
    }

    public String verificarEvolucion(Mascota mascota){

    Evolucion evolucion = evolucionRepository
        .findByTipoOrigen(mascota.getTipoMascota())
        .orElse(null);

         if(evolucion != null && mascota.getNivel().getId_nivel() >= evolucion.getNivel().getId_nivel()){

           return "Tu mascota "+mascota.getNombre()+" Evolucionó!";
        }
        return mascota.getNombre()+" subió de nivel.";
    }

    public EvolucionDTO crearEvolucion(Evolucion evo){
            evolucionRepository.save(evo);
        return convertirADTO(evo);
        }
     
    public Evolucion editarEvolucion(Integer id,Evolucion evo){
          Evolucion evoeditado = evolucionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Esa Evolucion no existe. Prueba con otro."));
        if(evoeditado.getNom_evo() != null){
            evo.setNom_evo(evoeditado.getNom_evo());
        }
        if(evoeditado.getNivel().getId_nivel() != null){
            evo.getNivel().setId_nivel(evoeditado.getNivel().getId_nivel());
        }
        if(evoeditado.getTipoMascota().getId() != null){
            evo.getTipoMascota().setId(evoeditado.getTipoMascota().getId());
        }
       
        return  evolucionRepository.save(evoeditado);
        
    }

    public String borrarEvolucion(Integer id) {
        Evolucion evo = evolucionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Esa Evolucion no existe. Prueba con otro."));
        if (evo.getId_evo() != null) {
            evolucionRepository.deleteById(evo.getId_evo());
            return "Evolución eliminada.";
        }
        return "No se pudo eliminar";
    }

    private EvolucionDTO convertirADTO(Evolucion evo) { // metodo DTO sin uso
        EvolucionDTO evoDTO = new EvolucionDTO();
        evoDTO.setId_evo(evo.getId_evo());
        evoDTO.setNom_evo(evo.getNom_evo());
        if(evo.getNivel().getId_nivel() != null){
           evoDTO.setNivelReq(evo.getNivel().getId_nivel());
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
