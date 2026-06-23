package com.example.Tienda_Gestion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Tienda_Gestion.DTO.ItemDTOExterno;
import com.example.Tienda_Gestion.DTO.TiendaItemDTO;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Repository.TiendaItemRepository;
import com.example.Tienda_Gestion.Repository.TiendaRepository;
import com.example.Tienda_Gestion.Service.Client.ItemClientService;
import com.example.Tienda_Gestion.Service.Client.UsuarioClientService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TiendaItemService {

    @Autowired
    private TiendaItemRepository tiendaItemRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private ItemClientService itemClientService;

    @Autowired
    private UsuarioClientService usuarioClientService;

    public TiendaItemDTO agregarItemATienda(TiendaItem tiendaItem) {

        log.info("Agregando ítem a tienda");

        Tienda tienda = tiendaRepository
                .findById(tiendaItem.getTienda().getIdTienda())
                .orElseThrow(() -> {
                    log.error("No se encontró la tienda");
                    return new RuntimeException("Tienda no encontrada");
                });

        Integer idItem = tiendaItem.getIdItemFk();

        log.info("Consultando ítem en Inventario-Gestión");

        ItemDTOExterno item = itemClientService.obtenerItem(idItem);

        TiendaItem nuevoItem = new TiendaItem();
        nuevoItem.setTienda(tienda);
        nuevoItem.setIdItemFk(item.getIdItem());

        TiendaItem itemGuardado = tiendaItemRepository.save(nuevoItem);

        log.info("Ítem agregado correctamente a la tienda");
        return convertirADTO(itemGuardado);
    }

    public void comprarItem(Integer idUsuario, Integer idTiendaItem) {
        log.info("Iniciando compra de ítem");

        TiendaItem tiendaItem = tiendaItemRepository.findById(idTiendaItem)
                .orElseThrow(() -> {
                    log.error("El ítem no está disponible");
                    return new RuntimeException("El ítem no está disponible");
                });

        Integer idItem = tiendaItem.getIdItemFk();

        log.info("Consultando ítem en Inventario-Gestión");

        itemClientService.obtenerItem(idItem);

        log.info("Consultando usuario");

        usuarioClientService.obtenerUsuario(idUsuario);

        log.info("Datos de compra validados correctamente");
    }

    private TiendaItemDTO convertirADTO(TiendaItem tiendaItem) {
        TiendaItemDTO tiDTO = new TiendaItemDTO();
        tiDTO.setIdTiendaItem(tiendaItem.getIdTiendaItem());
        tiDTO.setId_item_FK(tiendaItem.getIdItemFk());
        tiDTO.setId_tienda_FK(tiendaItem.getTienda());

        return tiDTO;
    }
}
