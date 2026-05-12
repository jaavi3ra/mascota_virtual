package com.example.mascotav.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    //crear mascota, se crea junto con estado mascota
    @PostMapping("/crear/{userid}")
    public ResponseEntity<?> crearMascota(@PathVariable Integer userid,@RequestBody Mascota mascota) {
        try {
            MascotaDTO mascotacreada = mascotaService.crearMascota(userid,mascota);
            
            Map<String, Object> response = new HashMap<>();

            response.put("Mensaje: ", "Mascota Creada.");
            response.put("Estado Mascota: ", mascotacreada.getTipoMascota());
            return new ResponseEntity<>(mascotacreada, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
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
