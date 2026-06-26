package com.example.Inventario_Gestion.Service;

import java.time.Duration;

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

    public AccionDTOExterno obtenerAccion(Integer idAccion) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://accion-service/api/v1/accion/{idAccion}", idAccion)
                    .retrieve()
                    .bodyToMono(AccionDTOExterno.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (Exception e) {
            log.error("error [getAccion]: ", e);
            throw new RuntimeException("No se pudo obtener la acción", e);
        }
    }

    public HistorialAccionDTOExterno registroHistorial(
            Integer idMascota,
            AccionDTOExterno accion,
            String descripcion) {
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
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (Exception e) {
            log.error("error [postRegistro]", e);
            throw new RuntimeException("No se pudo registrar el historial", e);
        }
    }
}
