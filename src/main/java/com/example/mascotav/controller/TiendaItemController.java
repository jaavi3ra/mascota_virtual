package com.example.mascotav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.model.TiendaItem;
import com.example.mascotav.service.TiendaItemService;

@RestController
@RequestMapping("/api/v1/tiendaItem")
public class TiendaItemController {
    @Autowired
    private TiendaItemService tiendaItemService;

    @PostMapping("/agregarItemTienda")
    public ResponseEntity<?> agregarItemaTienda(@RequestBody TiendaItem tiendaItem){
       try{
        tiendaItemService.agregarItemATienda(tiendaItem);
        return new ResponseEntity<>("Item agregado a la tienda!",HttpStatus.CREATED);
       }catch(RuntimeException e){
        return new ResponseEntity<>("No se pudo agregar :(", HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping("/comprarItems/{isuser},{iditem}")
    public ResponseEntity<?> comprarItem(@PathVariable Integer isuser, Integer iditem){
       try {
        tiendaItemService.comprarItem(isuser, iditem);
        return new ResponseEntity<>("Item comprado y añadido al invetario!.",HttpStatus.CREATED);
       } catch(RuntimeException e){
        return new ResponseEntity<>("No se pudo comprar :(", HttpStatus.BAD_REQUEST);
       }
        
    }
}
