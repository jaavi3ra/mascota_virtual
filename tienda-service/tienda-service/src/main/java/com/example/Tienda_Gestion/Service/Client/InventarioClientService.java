package com.example.Tienda_Gestion.Service.Client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Tienda_Gestion.DTO.InventarioDTOExterno;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InventarioClientService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public InventarioDTOExterno obtenerInventario(Integer idinvent){
        try{
            return webClientBuilder.build()
                .get()
                .uri("http://inventario-service/api/v1/inventario/{idinvent}" , idinvent)
                .retrieve()
                .bodyToMono(InventarioDTOExterno.class)              
                .timeout(Duration.ofSeconds(5))
                .block();
        }catch(Exception e){
            log.error("error [getinventario1]: ",e);
            throw new RuntimeException("No se pudo obtener el inventario", e);
        }
         
    }

    public InventarioDTOExterno findByUsuarioAndItem(Integer userid, Integer itemid){
        try{
            return webClientBuilder.build()
                .get()
                .uri("http://inventario-service/api/v1/inventario/{userid}/buscaritem/{itemid}",
                    userid, 
                    itemid)
                .exchangeToMono(response -> {
                    // Un 404 significa que el usuario todavía no tiene este ítem.
                    // Se devuelve vacío para que Tienda pueda crear su primer registro de inventario.
                    if (response.statusCode().value() == 404) {
                        return Mono.empty();
                    }
                    if (response.statusCode().isError()) {
                        return response.createException().flatMap(Mono::error);
                    }
                    return response.bodyToMono(InventarioDTOExterno.class);
                })
                .timeout(Duration.ofSeconds(5))
                .block();
        }catch(Exception e){
            log.error("error [getinventario2]: ",e);
            throw new RuntimeException("No se pudo consultar el inventario", e);
        }
    }

    public InventarioDTOExterno guardarInventario(InventarioDTOExterno inventario){
        try{
            return webClientBuilder.build()
                .post()
                .uri("http://inventario-service/api/v1/inventario/save-inventario")
                .bodyValue(inventario)
                .retrieve()
                .bodyToMono(InventarioDTOExterno.class)              
                .timeout(Duration.ofSeconds(5))
                .block();
        }catch(Exception e){
            log.error("error [postinventario3]: ",e);
            throw new RuntimeException("No se pudo guardar el inventario", e);
        }
    }
    public InventarioDTOExterno actualizarInventario(InventarioDTOExterno inventario){
        try{
            return webClientBuilder.build()
                .patch()
                .uri("http://inventario-service/api/v1/inventario/update-inventario")
                .bodyValue(inventario)
                .retrieve()
                .bodyToMono(InventarioDTOExterno.class)              
                .timeout(Duration.ofSeconds(5))
                .block();
        }catch(Exception e){
            log.error("error [patchinventario4]: ",e);
            throw new RuntimeException("No se pudo actualizar el inventario", e);
        }
    }
}
