package com.example.Inventario_Gestion.Controller.V2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventario_Gestion.Assemblers.InventarioModelAssembler;
import com.example.Inventario_Gestion.DTO.InventarioDTO;
import com.example.Inventario_Gestion.Model.Inventario;
import com.example.Inventario_Gestion.Service.InventarioService;

import jakarta.validation.Valid;

@RestController("InventarioControllerV2")
@RequestMapping("/api/v2/inventario")

public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InventarioDTO>> buscarporidInventario(@PathVariable Integer id) {
        try {
            InventarioDTO dto = inventarioService.obtenerInventariobyId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/usuario/{iduser}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<InventarioDTO>>> listarItemdDeInventario(
            @PathVariable Integer iduser) {
        try {
            List<EntityModel<InventarioDTO>> items = inventarioService
                    .listarItemdelInventario(iduser)
                    .stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());

            if (items.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(
                    CollectionModel.of(
                            items,
                            linkTo(methodOn(InventarioController.class)
                                    .listarItemdDeInventario(iduser))
                                    .withSelfRel()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{userid}/buscaritem/{itemid}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InventarioDTO>> obtenerItemenInventario(@PathVariable Integer userid,
            @PathVariable Integer itemid) {
        try {
            InventarioDTO dto = inventarioService.obtenerItemdelInventario(userid, itemid);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/save-inventario", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InventarioDTO>> saveInventario(@Valid @RequestBody Inventario inventario) {

        try {
            InventarioDTO dto = inventarioService.guardarInventario(inventario);
            return ResponseEntity
                    .created(linkTo(methodOn(InventarioController.class).buscarporidInventario(dto.getId_inven()))
                            .toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/update-inventario")
    public ResponseEntity<EntityModel<InventarioDTO>> updateInventario(@Valid @RequestBody Inventario inventario) {
        try {
            InventarioDTO dto = inventarioService.guardarInventario(inventario);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
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
