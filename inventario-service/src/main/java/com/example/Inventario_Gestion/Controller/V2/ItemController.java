package com.example.Inventario_Gestion.Controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventario_Gestion.Assemblers.ItemModelAssembler;
import com.example.Inventario_Gestion.DTO.ItemDTO;
import com.example.Inventario_Gestion.Model.Item;
import com.example.Inventario_Gestion.Service.ItemService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("ItemControllerV2")
@RequestMapping("/api/v2/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ItemDTO>> obtenerItenPorId(@PathVariable Integer id) {
        try {
            ItemDTO item = itemService.obtenerItemId(id);
            return ResponseEntity.ok(assembler.toModel(item));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ItemDTO>>> listar() {
        List<EntityModel<ItemDTO>> items = itemService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                items,
                linkTo(methodOn(ItemController.class).listar()).withSelfRel()));
    }

    @PostMapping(value = "/crearitem", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ItemDTO>> crear(@Valid @RequestBody Item item) {
        try {
            ItemDTO dto = itemService.guardar(item);
            return ResponseEntity
                    .created(linkTo(methodOn(ItemController.class).obtenerItenPorId(dto.getIdItem())).toUri())
                    .body(assembler.toModel(dto));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
