package com.example.Tienda_Gestion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Tienda_Gestion.DTO.ItemDTOExterno;
import com.example.Tienda_Gestion.DTO.TiendaItemDTO;
import com.example.Tienda_Gestion.DTO.UsuarioDTOExterno;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Repository.TiendaItemRepository;
import com.example.Tienda_Gestion.Repository.TiendaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TiendaItemService {

    @Autowired
    private TiendaItemRepository tiendaItemRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public TiendaItemDTO agregarItemATienda(TiendaItem tiendaItem) {

        Tienda tienda = tiendaRepository
                .findById(tiendaItem.getTienda().getIdTienda())
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

        Integer idItem = tiendaItem.getIdItemFk();

        ItemDTOExterno item = webClientBuilder.build()
                .get()
                .uri("http://inventario_gestion-service/api/v1/item/{id}", idItem)
                .retrieve()
                .bodyToMono(ItemDTOExterno.class)
                .block();

        TiendaItem nuevoItem = new TiendaItem();
        nuevoItem.setTienda(tienda);
        nuevoItem.setIdItemFk(item.getIdItem());
        nuevoItem.setCooldownSegundos(180); // 30 min para toda compra

        TiendaItem itemGuardado = tiendaItemRepository.save(nuevoItem);

        // convertirADTO(tItem);
        return convertirADTO(itemGuardado);
    }

    public void comprarItem(Integer idUsuario, Integer idTiendaItem) {

        TiendaItem tiendaItem = tiendaItemRepository.findById(idTiendaItem)
                .orElseThrow(() -> new RuntimeException("El ítem no está disponible"));

        Integer idItem = tiendaItem.getIdItemFk();
        Integer cooldown = tiendaItem.getCooldownSegundos();

        ItemDTOExterno item = webClientBuilder.build()
                .get()
                .uri("http://inventario_gestion-service/api/v1/item/{id}", idItem)
                .retrieve()
                .bodyToMono(ItemDTOExterno.class)
                .block();

        if (item == null) {
            throw new RuntimeException("No se pudo obtener el ítem");
        }

        UsuarioDTOExterno usuario = webClientBuilder.build()
                .get()
                .uri("http://usuario-service/api/v1/usuario/buscar-iduser/{iduser}", idUsuario)
                .retrieve()
                .bodyToMono(UsuarioDTOExterno.class)
                .block();

        if (usuario == null) {
            throw new RuntimeException("No se pudo obtener el usuario");
        }
    }

    private TiendaItemDTO convertirADTO(TiendaItem tiendaItem) {
        TiendaItemDTO tiDTO = new TiendaItemDTO();
        tiDTO.setIdTiendaItem(tiendaItem.getIdTiendaItem());
        tiDTO.setCooldown_segundos(tiendaItem.getCooldownSegundos());
        tiDTO.setId_item_FK(tiendaItem.getIdItemFk());
        tiDTO.setId_tienda_FK(tiendaItem.getTienda());

        return tiDTO;
    }
}
