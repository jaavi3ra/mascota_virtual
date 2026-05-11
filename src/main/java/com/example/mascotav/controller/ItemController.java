package com.example.mascotav.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.ItemDTO;
import com.example.mascotav.model.Item;
import com.example.mascotav.service.ItemService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    
    @GetMapping
    public ResponseEntity<List<ItemDTO>> listar() {
        return new ResponseEntity<>(itemService.obtenerTodos(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Item> crear(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.guardar(item), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            itemService.eliminar(id);
            return ResponseEntity.ok("Item eliminado correctamente");
        }catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para darle un item del inventario a la mascota
    @PostMapping("/usar")
    public ResponseEntity<String> darItem
    (@RequestParam Integer idUsuario, @RequestParam Integer idItem, @RequestParam Integer idMascota) {
        try {
            // Se llama el metodo
            String mensaje = itemService.darItem(idUsuario, idItem, idMascota);
            // Si sale bien devuelve un 200 con mensaje de exito
            return ResponseEntity.ok(mensaje);

        } catch(RuntimeException e) {
            // si algo falla deuvelve un 400 con mensaje de error
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
