package com.example.Tienda_Gestion.Controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Tienda_Gestion.DTO.TiendaDTO;
import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.Service.TiendaService;
import com.example.Tienda_Gestion.assemblers.TiendaModelAssembler;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("tiendaControllerV2")
@RequestMapping("/api/v2/tienda")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private TiendaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TiendaDTO>>> todas() {
        List<EntityModel<TiendaDTO>> tiendas = tiendaService.listarTodasTiendas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (tiendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tiendas,
                linkTo(methodOn(TiendaController.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaDTO>> porId(@PathVariable Integer id) {
        try {
            TiendaDTO dto = tiendaService.obtenerPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaDTO>> registrar(@Valid @RequestBody Tienda tienda) {
        try {
            Tienda nuevaTienda = tiendaService.guardarTienda(tienda);
            TiendaDTO dto = tiendaService.obtenerPorId(nuevaTienda.getIdTienda());
            return ResponseEntity
                    .created(linkTo(methodOn(TiendaController.class).porId(dto.getIdTienda())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
