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
import com.netflix.discovery.converters.Auto;

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
        nuevoItem.setCooldownSegundos(180); // 30 min para toda compra

        TiendaItem itemGuardado = tiendaItemRepository.save(nuevoItem);

        log.info("Ítem agregado correctamente a la tienda");
        return convertirADTO(itemGuardado);
    }

    public void comprarItem(Integer idUsuario, Integer idItem) {
        log.info("Iniciando compra de ítem...");

        log.info("obteniendo usuario...");
        UsuarioDTOExterno usuario =  usuarioClientService
            .obtenerUsuario(idUsuario);       

        log.info("obteniendo ítem ...");
        ItemDTOExterno item = itemClientService
            .obtenerItem(idItem);
     
        agregarItemInventario(usuario.getIdUsuario(), item.getIdItem());
    }

    private void agregarItemInventario(Integer userid, Integer itemid){
        try{
            Optional<InventarioDTOExterno> inventarioExistente = Optional.of(inventarioClientService
                    .findByUsuarioAndItem(userid, itemid));
            if(inventarioExistente!=null){
                log.info("Consultando ítem en Inventario...");
                        InventarioDTOExterno inventario;    
                            if(inventarioExistente.isPresent()){
                                inventario = inventarioExistente.get();
                                log.info("agregando +1 ítem en Inventario...");
                                inventario.setCantidad(
                                    inventario.getCantidad() + 1
                                );

                                inventarioClientService
                                    .actualizarInventario(inventario);
                            }else{
                                // crear nuevo inventario
                                log.info("creando ítem en Inventario...");
                                inventario = new InventarioDTOExterno();
                                inventario.setUsuario(userid);
                                inventario.setItem(itemid);
                                inventario.setCantidad(1);

                                inventarioClientService
                                    .guardarInventario(inventario);
                            }
                        log.info("Datos de compra validados correctamente"); 
            }else{
                log.error("error [objetoinventario]: ", inventarioExistente);
            }
                
        }catch(Exception e){
            log.error("error [addsItemaInvent]: ", e);
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
