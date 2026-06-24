package com.example.Inventario_Gestion.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Inventario_Gestion.DTO.ItemDTO;
import com.example.Inventario_Gestion.Model.Item;
import com.example.Inventario_Gestion.Service.ItemService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerItenPorId(@PathVariable Integer id) {
        try {
            ItemDTO item = itemService.obtenerItemId(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listar() {
        return new ResponseEntity<>(itemService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping("/crearitem")
    public ResponseEntity<?> crear(@Valid @RequestBody Item item) {
        try {
            return new ResponseEntity<>(itemService.guardar(item), HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}