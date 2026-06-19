package com.example.Tienda_Gestion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Service.TiendaItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tiendaItem")
public class TiendaItemController {

   @Autowired
   private TiendaItemService tiendaItemService;

   @PostMapping("/agregarItemTienda")
   public ResponseEntity<?> agregarItemaTienda(@Valid @RequestBody TiendaItem tiendaItem) {
      try {
         tiendaItemService.agregarItemATienda(tiendaItem);
         return new ResponseEntity<>("Item agregado a la tienda!", HttpStatus.CREATED);
      } catch (RuntimeException e) {
         return new ResponseEntity<>("No se pudo agregar :(", HttpStatus.BAD_REQUEST);
      }
   }

   @PostMapping("/{isuser}/comprarItems/{iditem}")
   public ResponseEntity<?> comprarItem(@PathVariable Integer isuser, @PathVariable Integer iditem) {
      try {
         tiendaItemService.comprarItem(isuser, iditem);
         return new ResponseEntity<>("Item comprado y añadido al invetario!.", HttpStatus.CREATED);
      } catch (RuntimeException e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }

   }
}
