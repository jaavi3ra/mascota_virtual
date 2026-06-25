package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascota.mascota_service.DTO.AccionDTOExterno;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.repository.EstadoMascotaRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j 
@Service
public class EstadoMascotaService {
        @Autowired
        private EstadoMascotaRepository estadoMascotaRepository;
        @Autowired
        private AccionClientService accionClientService;

       public EstadoMascota iniciarEstado(Mascota mascota){
        log.info("Creando estado mascota...");
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

    public EstadoMascota editarEstado(Integer idestadopet, Integer idaccion){
        try{
            EstadoMascota estadonuevo = estadoMascotaRepository
                    .findById(idestadopet)
                    .orElseThrow(() ->  new RuntimeException("Tipo mascota no encontrado"));

            AccionDTOExterno accion = accionClientService
                    .obtenerAccion(idaccion);
                    
            estadonuevo.setEnergia(accion.getAfectaEnergia() + estadonuevo.getEnergia());
            estadonuevo.setFelicidad(accion.getAfectaFelicidad() + estadonuevo.getFelicidad());
            estadonuevo.setHambre(accion.getAfectaHambre() + estadonuevo.getHambre());
            estadonuevo.setSalud(accion.getAfectaSalud() + estadonuevo.getSalud());
            estadoMascotaRepository.save(estadonuevo);
            log.info("estado de mascota editada.");
            return estadonuevo;
        }catch(Exception e){
            log.error("No se pudo editar estado: ", e);
            throw new RuntimeException("No se pudo editar el estado de la mascota", e);
        }

    }
}
