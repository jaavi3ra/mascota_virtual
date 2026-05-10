package com.example.mascotav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.service.Usuario_tienda_item_Service;




@RestController
@RequestMapping("/api/tienda")
public class UsuarioTiendaItemController {
    @Autowired
    private Usuario_tienda_item_Service usuarioTiendaItemService;

    @PostMapping("/{idUsuario}/comprar/{idTiendaItem}")
    public ResponseEntity<?> comprarItem(@PathVariable Integer idUsuario,@PathVariable Integer idTiendaItem) {
        try {
            usuarioTiendaItemService.crearcompra(idUsuario, idTiendaItem);
            return new ResponseEntity<>("Item agregado al inventario correctamente", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("error al comprar item",  HttpStatus.BAD_REQUEST);

        }
    }
}
