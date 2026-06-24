package com.example.Tienda_Gestion.Service.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Tienda_Gestion.DTO.InventarioDTOExterno;

import lombok.extern.slf4j.Slf4j;

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
                .block();
        }catch(Exception e){
            log.error("error [getinventario1]: ",e);
            return null;
        }
         
    }

    public InventarioDTOExterno findByUsuarioAndItem(Integer userid, Integer itemid){
        try{
            return webClientBuilder.build()
                .get()
                .uri("http://inventario-service/api/v1/inventario/{userid}/buscaritem/{itemid}",
                    userid, 
                    itemid)
                .retrieve()
                .bodyToMono(InventarioDTOExterno.class)              
                .block();
        }catch(Exception e){
            log.error("error [getinventario2]: ",e);
            return null;
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
                .block();
        }catch(Exception e){
            log.error("error [patchinventario4]: ",e);
            throw new RuntimeException("No se pudo actualizar el inventario", e);
        }
    }
}
