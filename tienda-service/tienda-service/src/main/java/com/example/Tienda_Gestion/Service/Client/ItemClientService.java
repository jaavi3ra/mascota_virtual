package com.example.Tienda_Gestion.Service.Client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Tienda_Gestion.DTO.ItemDTOExterno;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ItemClientService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ItemDTOExterno obtenerItem(Integer idItem) {
        try {
            ItemDTOExterno item = webClientBuilder.build()
                    .get()
                    .uri("http://inventario-service/api/v1/item/{id}", idItem)
                    .retrieve()
                    .onStatus(status -> status.isError(),
                            response -> Mono.error(new RuntimeException("Error al consultar el ítem")))
                    .bodyToMono(ItemDTOExterno.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (item == null) {
                throw new RuntimeException("No se recibió información del ítem");
            }

            return item;

        } catch (RuntimeException e) {
            log.error("No se pudo consultar el ítem");
            throw new RuntimeException("No se pudo obtener el ítem", e);
        }
    }
}
