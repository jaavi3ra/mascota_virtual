package com.example.mascotav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotav.DTO.TiendaItemDTO;
import com.example.mascotav.model.TiendaItem;
import com.example.mascotav.repository.TiendaItemRepository;
import com.example.mascotav.service.TiendaItemService;

@RestController
@RequestMapping("/api/v1/tiendaItem")
public class TiendaItemController {
    @Autowired
    private TiendaItemService tiendaItemService;

    @PostMapping("/agregarItemTienda")
    public ResponseEntity<?> agregarItemaTienda(@RequestBody TiendaItem tiendaItem){
        tiendaItemService.agregarItemATienda(tiendaItem);
        return new ResponseEntity<>("Item agregado a la tienda!",HttpStatus.CREATED);
    }
}
