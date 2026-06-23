package com.example.Tienda_Gestion.Service.Client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Tienda_Gestion.DTO.UsuarioDTOExterno;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UsuarioClientService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public UsuarioDTOExterno obtenerUsuario(Integer idUsuario) {
        try {
            UsuarioDTOExterno usuario = webClientBuilder.build()
                    .get()
                    .uri("http://usuario-service/api/v1/usuario/{id}", idUsuario)
                    .retrieve()
                    .onStatus(status -> status.isError(),
                            response -> Mono.error(new RuntimeException("Error al consultar el usuario")))
                    .bodyToMono(UsuarioDTOExterno.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (usuario == null) {
                throw new RuntimeException("No se recibió información del usuario");
            }

            return usuario;

        } catch (RuntimeException e) {
            log.error("No se pudo consultar el usuario");
            throw new RuntimeException("No se pudo obtener el usuario", e);
        }
    }
}
