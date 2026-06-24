package com.example.Inventario_Gestion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventario_Gestion.DTO.InventarioDTO;
import com.example.Inventario_Gestion.Model.Inventario;
import com.example.Inventario_Gestion.Service.InventarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventario")

public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporidInventario(@PathVariable Integer id){
        try{
            Inventario inventario = inventarioService.obtenerInventariobyId(id);
            return new ResponseEntity<>(inventario, HttpStatus.OK);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userid}/buscaritem/{itemid}")
    public ResponseEntity<?> obtenerItemenInventario(@PathVariable Integer userid, @PathVariable Integer itemid){
        try{
            Inventario inventario = inventarioService.obtenerItemdelInventario(userid,itemid);
            return new ResponseEntity<>(inventario, HttpStatus.OK);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{iduser}")
    public ResponseEntity<?> listarItemdDeInventario( @PathVariable Integer iduser) {
        try {
            List<InventarioDTO> items = inventarioService.listarItemdelInventario(iduser);
            return items.isEmpty()
                    ? new ResponseEntity<>("No hay items en el inventario de este usuario.", HttpStatus.OK)
                    : new ResponseEntity<>(items, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/save-inventario")
    public ResponseEntity<?> saveInventario(@Valid @RequestBody Inventario inventario){
        try{
            Inventario inventarionuevo = inventarioService.guardarInventario(inventario);
            return new ResponseEntity<>(inventarionuevo, HttpStatus.CREATED);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update-inventario")
    public ResponseEntity<?> updateInventario(@Valid @RequestBody Inventario inventario){
        try{
            Inventario inventarionuevo = inventarioService.guardarInventario(inventario);
            return new ResponseEntity<>(inventarionuevo, HttpStatus.CREATED);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{idmascota}/darItem/{iditem}")
    // editar estado de mascotaid con itemId
    public ResponseEntity<?> usarItem(@PathVariable Integer idmascota, @PathVariable Integer iditem) {
        try {
            String mensaje = inventarioService.usarItem(idmascota, iditem);
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}