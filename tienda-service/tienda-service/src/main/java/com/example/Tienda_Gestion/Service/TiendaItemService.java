package com.example.Tienda_Gestion.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Tienda_Gestion.DTO.InventarioDTOExterno;
import com.example.Tienda_Gestion.DTO.ItemDTOExterno;
import com.example.Tienda_Gestion.DTO.TiendaItemDTO;
import com.example.Tienda_Gestion.DTO.UsuarioDTOExterno;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Repository.TiendaItemRepository;
import com.example.Tienda_Gestion.Repository.TiendaRepository;
import com.example.Tienda_Gestion.Service.Client.InventarioClientService;
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

    @Autowired
    private InventarioClientService inventarioClientService;

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
        log.info("Iniciando compra de ítem...");

        TiendaItem tiendaItem = tiendaItemRepository.findById(idTiendaItem)
                .orElseThrow(() -> new RuntimeException("El ítem no está disponible en la tienda"));

        Integer idItem = tiendaItem.getIdItemFk();

        log.info("obteniendo usuario...");
        UsuarioDTOExterno usuario = usuarioClientService
                .obtenerUsuario(idUsuario);

        log.info("obteniendo ítem ...");
        ItemDTOExterno item = itemClientService
                .obtenerItem(idItem);

        agregarItemInventario(usuario.getIdUsuario(), item.getIdItem());
    }

    private void agregarItemInventario(Integer userId, Integer itemId) {
        Optional<InventarioDTOExterno> inventarioExistente = Optional.ofNullable(
                inventarioClientService.findByUsuarioAndItem(userId, itemId));

        InventarioDTOExterno resultado;

        if (inventarioExistente.isPresent()) {
            InventarioDTOExterno inventario = inventarioExistente.get();
            inventario.setCantidad(inventario.getCantidad() + 1);
            resultado = inventarioClientService.actualizarInventario(inventario);
        } else {
            InventarioDTOExterno inventario = new InventarioDTOExterno();
            ItemDTOExterno item = new ItemDTOExterno();
            item.setIdItem(itemId);

            inventario.setIdUserFk(userId);
            inventario.setItem(item);
            inventario.setCantidad(1);
            resultado = inventarioClientService.guardarInventario(inventario);
        }

        if (resultado == null) {
            throw new RuntimeException("No se pudo registrar la compra en Inventario");
        }

        log.info("Compra registrada correctamente");
    }

    private TiendaItemDTO convertirADTO(TiendaItem tiendaItem) {
        TiendaItemDTO tiDTO = new TiendaItemDTO();
        tiDTO.setIdTiendaItem(tiendaItem.getIdTiendaItem());
        tiDTO.setId_item_FK(tiendaItem.getIdItemFk());
        tiDTO.setId_tienda_FK(tiendaItem.getTienda());

        return tiDTO;
    }
}
