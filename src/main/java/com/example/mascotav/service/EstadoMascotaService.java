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
        
           estadoRepository.save(estado);
        return estado;
        
    }

    public EstadoMascotaDTO buscarPorId(Integer id) {
        EstadoMascota estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return convertirADTO(estado);
    }


    public void verificarLimitesYSalud(EstadoMascota estado) {

        // Asegura que nada pase de 100 ni baje de 0
        estado.setHambre(Math.min(100, Math.max(0, estado.getHambre())));
        estado.setFelicidad(Math.min(100, Math.max(0, estado.getFelicidad())));
        estado.setEnergia(Math.min(100, Math.max(0, estado.getEnergia())));
        estado.setSalud(Math.min(100, Math.max(0, estado.getSalud())));

        // REGLA CRÍTICA: Hambre llega a 0, explota todo
        if (estado.getHambre() <= 0) {
            estado.setSalud(0);
            estado.setFelicidad(0);
            estado.setEnergia(0);

            throw new RuntimeException("¡La mascota implosiono y ha muerto!");
        }
    }

    public EstadoMascotaDTO aplicarEfecto(Mascota mascota, Item item){

        EstadoMascota estado = mascota.getEstadoMascota();

        estado.setFelicidad(
            estado.getFelicidad() + item.getAccion().getAfectaFelicidad()
        );
        estado.setEnergia(
            estado.getEnergia() + item.getAccion().getAfectaEnergia()
        );
        estado.setHambre(
            estado.getHambre() + item.getAccion().getAfectaHambre()
        );

        estado.setSalud(
            estado.getSalud() + item.getAccion().getAfectaSalud()
        );
        mascota.setExpActual(
            mascota.getExpActual() +item.getAccion().getAfectaExpBase()
        );

        // guardar estado actualizado
        estadoRepository.save(estado);

        return convertirADTO(estado);
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
