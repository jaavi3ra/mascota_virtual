package com.example.Inventario_Gestion.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.Inventario_Gestion.DTO.AccionDTOExterno;
import com.example.Inventario_Gestion.DTO.EstadoMascotaDTOExterno;
import com.example.Inventario_Gestion.DTO.MascotaDTOExterno;
import com.example.Inventario_Gestion.Model.Item;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MascotaClientService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private AccionClientService accionClientService;

    public MascotaDTOExterno obtenerMascota(Integer idmascota){
        try{
            return webClientBuilder.build()
                .get()
                .uri("http://mascota-service/api/v1/mascota/buscar-pet/{idmascota}", idmascota)
                .retrieve()
                .bodyToMono(MascotaDTOExterno.class)
                .block();
        }catch(Exception e){
            log.error("error [getpet]: ",e.getMessage());
            return null;
        }
           
    }
        public MascotaDTOExterno guardarMascota(MascotaDTOExterno mascota){
          try{
             return webClientBuilder.build()
                .post()
                .uri("http://mascota-service/api/v1/mascota/")
                .bodyValue(mascota)
                .retrieve()
                .bodyToMono(MascotaDTOExterno.class)
                .block();
          }catch(Exception e){
            log.error("error [savepet]: ",e.getMessage());
            return null;
          }
           
    }

    public MascotaDTOExterno actualizarExpMascota(MascotaDTOExterno mascota, int afectaExpBase){
          try{
             return webClientBuilder.build()
                .put()
                .uri("http://mascota-service/api/v1/mascota/actualizarExp/{afectaExpBase}",afectaExpBase)
                .bodyValue(mascota)
                .retrieve()
                .bodyToMono(MascotaDTOExterno.class)
                .block();

          }catch(Exception e){
            log.error("error [updatepet]: ",e.getMessage());
            return null;
          }
           
    }
        public EstadoMascotaDTOExterno editarEstadoMascota(Integer idestado, Integer idaccion){
          try{
             return webClientBuilder.build()
                .put()
                .uri("http://mascota-service/api/v1/estado/editar-estado/{idestado}",idestado)
                .bodyValue(idaccion)
                .retrieve()
                .bodyToMono(EstadoMascotaDTOExterno.class)
                .block();

          }catch(Exception e){
            log.error("error [updateStatpet]: ",e.getMessage());
            return null;
          }
           
    }


    public void aplicarEfectos(MascotaDTOExterno mascota, Item item ){
            AccionDTOExterno accion = accionClientService
                .obtenerAccion(item.getIdAccionFk());
                
           // MascotaDTOExterno mascotaActualizada = 
                actualizarExpMascota(mascota, accion.getAfectaExpBase());

            //EstadoMascotaDTOExterno estado = 
                editarEstadoMascota( mascota.getEstado().getIdEstadoMascota(), accion.getIdAccion());
            
    }

}
