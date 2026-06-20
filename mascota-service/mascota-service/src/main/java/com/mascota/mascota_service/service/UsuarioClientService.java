package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mascota.mascota_service.DTO.UsuarioDTOExterno;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UsuarioClientService {
    @Autowired
    private WebClient.Builder webClientBuilder;

   public Integer obtenerUsuario(Integer iduser){

            return webClientBuilder.build()
                .get()
                .uri("http://usuario-service/api/v1/usuario/buscar-iduser" + iduser)
                .retrieve()
                // Manejo de errores 4xx o 5xx del microservicio externo
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Usuario no encontrado."))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> 
                    Mono.error(new RuntimeException("Error en el servidor de usuarios"))
                )
                .bodyToMono(UsuarioDTOExterno.class)
                .map(UsuarioDTOExterno::getIdUsuario)
                .block();
    }
}
