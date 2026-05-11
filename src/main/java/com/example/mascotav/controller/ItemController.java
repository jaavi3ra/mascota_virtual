package com.example.mascotav.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.ItemDTO;
import com.example.mascotav.model.Item;
import com.example.mascotav.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> crear(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.guardar(item), HttpStatus.CREATED);
    }
}
