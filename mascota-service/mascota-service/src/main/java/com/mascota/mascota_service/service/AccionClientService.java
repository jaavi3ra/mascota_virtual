package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mascota.mascota_service.DTO.AccionDTOExterno;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccionClientService {
 @Autowired
    private WebClient.Builder webClientBuilder;

   public AccionDTOExterno obtenerAccion(Integer idaccion){
        try{
            return webClientBuilder.build()
                .get()
                .uri("http://accion-service/api/v1/accion/{idaccion}" , idaccion)
                .retrieve()
                .bodyToMono(AccionDTOExterno.class)              
                .block();
        }catch(Exception e){
            log.error("error [getaccion]: ",e);
            return null;
        }
         
    }
}
