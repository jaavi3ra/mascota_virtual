package com.example.Inventario_Gestion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Inventario_Gestion.DTO.AccionDTOExterno;
import com.example.Inventario_Gestion.DTO.HistorialAccionDTOExterno;

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
                .uri("http://accion-service/api/v1/accion/{idaccion}", idaccion)
                .retrieve()
                .bodyToMono(AccionDTOExterno.class)
                .block();
        }catch(Exception e){
            log.error("error [getAccion]: ",e);
            return null;
        }

    }

    public HistorialAccionDTOExterno registroHistorial(Integer idMascota, AccionDTOExterno accion, String descripcion) {
        try {
            return webClientBuilder.build()
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("http://accion-service/api/v1/historial/mascota/{idMascota}")
                            .queryParam("descripcion", descripcion)
                            .build(idMascota))
                    .bodyValue(accion)
                    .retrieve()
                    .bodyToMono(HistorialAccionDTOExterno.class)
                    .block();

        } catch (Exception e) {
            log.error("error [postRegistro]", e);
            return null;
        }
    }

}
