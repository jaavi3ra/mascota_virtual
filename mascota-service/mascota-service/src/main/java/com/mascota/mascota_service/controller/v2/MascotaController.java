package com.mascota.mascota_service.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.assemblers.MascotaAssembler;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.service.MascotaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private MascotaAssembler assembler;

     @GetMapping(value = "/buscar-pet/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MascotaDTO>> buscarMascota(@PathVariable Integer id) {
        try {
            MascotaDTO mascotaDTO = mascotaService.obtenerMascotaId(id);
            return ResponseEntity.ok(assembler.toModel(mascotaDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

     @PostMapping(value = "/crear-mascota/{userid}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MascotaDTO>> crearMascota(@PathVariable Integer userid, @Valid @RequestBody String nombre, Integer idtipo) {

        try {
            MascotaDTO mascotaCreada = mascotaService.crearMascota(userid, nombre, idtipo);

            return ResponseEntity
                    .created(linkTo(methodOn(MascotaController.class)
                            .buscarMascota(mascotaCreada.getIdMascota())).toUri())
                    .body(assembler.toModel(mascotaCreada));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

     @PutMapping(value = "/actualizarExp/{afectaExpBase}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MascotaDTO>> actualizarExp( @PathVariable int afectaExpBase, @Valid @RequestBody Mascota mascota) {

        try {
            MascotaDTO mascotaDTO = mascotaService.actualizarExpMascota(mascota, afectaExpBase);
            return ResponseEntity.ok(assembler.toModel(mascotaDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

        @PostMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MascotaDTO>> guardarMascota( @Valid @RequestBody Mascota mascota) {

        try {
            MascotaDTO mascotaDTO = mascotaService.guardarMascota(mascota);

            return ResponseEntity
                    .created(linkTo(methodOn(MascotaController.class)
                            .buscarMascota(mascotaDTO.getIdMascota())).toUri())
                    .body(assembler.toModel(mascotaDTO));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
