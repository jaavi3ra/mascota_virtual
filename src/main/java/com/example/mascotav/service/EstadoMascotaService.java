package com.example.mascotav.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.EstadoMascotaDTO;
import com.example.mascotav.model.EstadoMascota;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.EstadoMascotaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoMascotaService {

    @Autowired
    private EstadoMascotaRepository estadoRepository;

    public List<EstadoMascotaDTO> obtenerTodos() { //no lo vamos usar
        return estadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EstadoMascota iniciarEstado(Mascota mascota){
    
        EstadoMascota estado = new EstadoMascota();

        // REGLA CRÍTICA: Hambre llega a 0, explota todo  
            estado.setHambre(100);
            estado.setSalud(100);
            estado.setFelicidad(100);
            estado.setEnergia(100);
            estado.setMascota(mascota);
            estado.getMascota().setNombre(mascota.getNombre());
        
           estadoRepository.save(estado);
        return estado;
        
    }

    public EstadoMascotaDTO buscarPorId(Integer id) {
        EstadoMascota estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return convertirADTO(estado);
    }

    public EstadoMascota editarEstado(Mascota estado, Item item){
            System.out.println("idestado: "+estado.getEstadoMascota());
            EstadoMascota estadonuevo = estadoRepository.findById(estado.getEstadoMascota().getIdEstado())
                    .orElseThrow(() ->  new RuntimeException("Tipo mascota no encontrado"));
           
            estadonuevo.setEnergia(item.getAccion().getAfectaEnergia() + estadonuevo.getEnergia());
            estadonuevo.setFelicidad(item.getAccion().getAfectaFelicidad() + estadonuevo.getFelicidad());
            estadonuevo.setHambre(item.getAccion().getAfectaHambre() + estadonuevo.getHambre());
            estadonuevo.setSalud(item.getAccion().getAfectaSalud() + estadonuevo.getSalud());
            estadoRepository.save(estadonuevo);
            return estadonuevo;
    }


    public void verificarLimitesYSalud(EstadoMascota estado) {

        // Asegura que nada pase de 100 ni baje de 0
        estado.setHambre(Math.min(200, Math.max(0, estado.getHambre())));
        estado.setFelicidad(Math.min(200, Math.max(0, estado.getFelicidad())));
        estado.setEnergia(Math.min(200, Math.max(0, estado.getEnergia())));
        estado.setSalud(Math.min(200, Math.max(0, estado.getSalud())));

        // REGLA CRÍTICA: Hambre llega a 0, explota todo
        if (estado.getHambre() <= 0) {
            estado.setSalud(0);
            estado.setFelicidad(0);
            estado.setEnergia(0);

            throw new RuntimeException("¡La mascota implosiono y ha muerto!");
        }
    }
   
    

    private EstadoMascotaDTO convertirADTO(EstadoMascota estado) {
        EstadoMascotaDTO estDTO = new EstadoMascotaDTO();
        estDTO.setIdEstadoMascota(estado.getIdEstado());
        estDTO.setEnergia(estado.getEnergia());
        estDTO.setFelicidad(estado.getFelicidad());
        estDTO.setHambre(estado.getHambre());
        estDTO.setSalud(estado.getSalud());

        return estDTO;
    }

}
