package com.example.Tienda_Gestion.Controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Tienda_Gestion.DTO.TiendaItemDTO;
import com.example.Tienda_Gestion.Model.TiendaItem;
import com.example.Tienda_Gestion.Service.TiendaItemService;
import com.example.Tienda_Gestion.assemblers.TiendaItemModelAssembler;

import jakarta.validation.Valid;

@RestController("tiendaItemControllerV2")
@RequestMapping("/api/v2/tiendaItem")
public class TiendaItemController {

    @Autowired
    private TiendaItemService tiendaItemService;

    @Autowired
    private TiendaItemModelAssembler assembler;

    @GetMapping(value = "/{idtienda}/buscaritem/{itemid}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaItemDTO>> buscarPorId(@PathVariable Integer idtienda,
            @PathVariable Integer itemid) {
        try {
            TiendaItemDTO dto = tiendaItemService.buscarPorId(idtienda, itemid);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/agregarItemTienda", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaItemDTO>> agregarItemaTienda(@Valid @RequestBody TiendaItem tiendaItem) {
        try {
            TiendaItemDTO creado = tiendaItemService.agregarItemATienda(tiendaItem);
            return ResponseEntity
                    .created(linkTo(methodOn(TiendaItemController.class)
                            .buscarPorId(creado.getId_tienda_FK().getIdTienda(), creado.getId_item_FK())).toUri())
                    .body(assembler.toModel(creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/{idUsuario}/comprarItems/{idTiendaItem}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<String>> comprarItem(@PathVariable Integer idUsuario,
            @PathVariable Integer idTiendaItem) {
        try {
            tiendaItemService.comprarItem(idUsuario, idTiendaItem);
            EntityModel<String> respuesta = EntityModel.of(
                    "Item comprado y anadido al inventario.",
                    linkTo(methodOn(TiendaItemController.class).comprarItem(idUsuario, idTiendaItem)).withSelfRel(),
                    linkTo(methodOn(TiendaItemController.class).agregarItemaTienda(null)).withRel("agregar-item-tienda"));
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
