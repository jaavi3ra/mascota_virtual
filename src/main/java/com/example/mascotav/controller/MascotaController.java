package com.example.mascotav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotav.DTO.MascotaDTO;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.service.MascotaService;

@RestController
@RequestMapping("/api/v1/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> todasLasMascotas() {
        List<MascotaDTO> mascota = mascotaService.obtenerTodos();
        if (mascota.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mascota, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            MascotaDTO masco = mascotaService.buscarPorId(id);
            return new ResponseEntity<>(masco, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/alimentar/{idItem}")
    public ResponseEntity<MascotaDTO> alimentar(@PathVariable Integer id, @PathVariable Integer idItem) {
        try {
            MascotaDTO mascotaAlimento = mascotaService.alimentarMascota(id, idItem);
            return new ResponseEntity<>(mascotaAlimento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/jugar")
    public ResponseEntity<MascotaDTO> jugar(@PathVariable Integer id) {
        try {
            MascotaDTO mascotaJugar = mascotaService.jugarConMascota(id);
            return new ResponseEntity<>(mascotaJugar, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/ganar-xp/{puntos}")
    public ResponseEntity<MascotaDTO> ganarExperiencia(@PathVariable Integer id, @PathVariable Integer puntos) {
        try {
            MascotaDTO mascotaXP = mascotaService.ganarExperiencia(id, puntos);
            return new ResponseEntity<>(mascotaXP, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> crearMascota(@RequestBody Mascota mascota) {
        try {
            Mascota guardada = mascotaService.guardarMascota(mascota);
            MascotaDTO mascotaDTO = mascotaService.buscarPorId(guardada.getIdMascota());
            return new ResponseEntity<>(mascotaDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMascota(@PathVariable Integer id) {
        String resultado = mascotaService.eliminar(id);

        if (resultado.contains("Exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
