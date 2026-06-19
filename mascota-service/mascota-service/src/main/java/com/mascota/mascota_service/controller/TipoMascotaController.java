package com.mascota.mascota_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mascota.mascota_service.DTO.TipoMascotaDTO;
import com.mascota.mascota_service.service.TipoMascotaService;

@RestController
@RequestMapping("/api/v1/tipomascota")
public class TipoMascotaController {
    @Autowired
    private TipoMascotaService tipoMascotaService;

    @PostMapping("/crear-tipos")
    public  ResponseEntity<?> crearTiposMascotas(){
           
        try{
              List<TipoMascotaDTO>tipos = tipoMascotaService.creartipo();
                return new ResponseEntity<>(tipos, HttpStatus.CREATED);
        }catch(RuntimeException e){
            
            return new ResponseEntity<>("No se pudo crear tipos de mascota", HttpStatus.BAD_REQUEST);
            
        }
            
    }
}
