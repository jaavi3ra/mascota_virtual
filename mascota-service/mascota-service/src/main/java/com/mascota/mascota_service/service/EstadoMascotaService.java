package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.repository.EstadoMascotaRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j 
@Service
public class EstadoMascotaService {
        @Autowired
        private EstadoMascotaRepository estadoMascotaRepository;

       public EstadoMascota iniciarEstado(Mascota mascota){
    
        EstadoMascota estado = new EstadoMascota();
        // REGLA CRÍTICA: Hambre llega a 0, explota todo  
            estado.setHambre(100);
            estado.setSalud(100);
            estado.setFelicidad(100);
            estado.setEnergia(100);
            estado.setMascota(mascota);
            estado.getMascota().setNombre(mascota.getNombre());
        
           estadoMascotaRepository.save(estado);
        return estado;
        
    }
}
