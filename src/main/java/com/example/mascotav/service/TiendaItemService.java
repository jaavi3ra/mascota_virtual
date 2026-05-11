package com.example.mascotav.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.TiendaItemDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Tienda;
import com.example.mascotav.model.TiendaItem;
import com.example.mascotav.model.Usuario;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.ItemRepository;
import com.example.mascotav.repository.TiendaItemRepository;
import com.example.mascotav.repository.TiendaRepository;
import com.example.mascotav.repository.UsuarioRepository;

@Service
public class TiendaItemService {
    @Autowired
    private TiendaItemRepository tiendaItemRepository;
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UsuarioRepository  usuarioRepository;
    @Autowired
    private InventarioRepository inventarioRepository;

    public TiendaItemDTO agregarItemATienda(TiendaItem tiendaItem) {

    Tienda tienda = tiendaRepository
        .findById(tiendaItem.getTienda().getIdTienda())
        .orElseThrow(() ->
            new RuntimeException("Tienda no encontrada"));

    Item item = itemRepository
        .findById(tiendaItem.getItem().getIdItem())
        .orElseThrow(() ->
            new RuntimeException("Item no encontrado"));

    TiendaItem tItem = new TiendaItem();

    tItem.setTienda(tienda);
    tItem.setItem(item);
    tItem.setCooldownSegundos(180); // 30 min para toda compra

    tiendaItemRepository.save(tItem);

        return convertirADTO(tItem);
    }

    public Inventario comprarItem(Integer idUsuario,Integer idItem) {

        //System.out.println("ID USUARIO: " + idUsuario);
        //System.out.println("ID ITEM: " + idItem);

    Usuario usuario = usuarioRepository
        .findById(idUsuario)
        .orElseThrow(() ->
            new RuntimeException("Usuario no encontrado"));

    Item item = itemRepository
        .findById(idItem)
        .orElseThrow(() ->
            new RuntimeException("Item no encontrado"));

    Optional<Inventario> inventarioExistente = inventarioRepository
    .findByUsuarioAndItem(usuario.getId(), item.getIdItem());

    Inventario inventario;

    // si ya tiene el item
    if(inventarioExistente.isPresent()){

        inventario = inventarioExistente.get();

        inventario.setCantidad(
            inventario.getCantidad() + 1);
    } else {
        // crear nuevo inventario
        inventario = new Inventario();
        inventario.setUsuario(usuario);
        inventario.setItem(item);
        inventario.setCantidad(1);
    }
    return  inventarioRepository.save(inventario);
}

    private TiendaItemDTO convertirADTO(TiendaItem tienda) {
        TiendaItemDTO tiDTO = new TiendaItemDTO();
        tiDTO.setId_tienda_item(tienda.getIdTiendaItem());
        tiDTO.setCooldown_segundos(tienda.getCooldownSegundos());
        tiDTO.getId_item_FK().setIdItem(tienda.getItem().getIdItem());
        tiDTO.getId_tienda_FK().setIdTienda(tienda.getTienda().getIdTienda());


        return tiDTO;
    }
}
