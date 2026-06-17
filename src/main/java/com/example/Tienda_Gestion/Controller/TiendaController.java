package com.example.Tienda_Gestion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.mascotav.DTO.TiendaDTO;
import com.example.mascotav.model.Tienda;
import com.example.mascotav.service.TiendaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/tienda")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @GetMapping
    public ResponseEntity<List<TiendaDTO>> listar() {
        return new ResponseEntity<>(tiendaService.obtenerTodas(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tienda> crear(@RequestBody Tienda tienda) {
        return new ResponseEntity<>(tiendaService.guardar(tienda), HttpStatus.CREATED);
    }
    
    // Actualizar nombre
    @PutMapping("/{id}/nombre")
    public ResponseEntity<TiendaDTO> actualizarNombre(@PathVariable Integer id, @RequestParam String nuevoNombre) {
        TiendaDTO actualizada = tiendaService.actualizarNombre(id, nuevoNombre);
        return ResponseEntity.ok(actualizada);
    }
}
