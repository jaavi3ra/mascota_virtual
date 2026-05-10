package com.example.mascotav.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.EstadoMascotaDTO;
import com.example.mascotav.model.EstadoMascota;
import com.example.mascotav.repository.EstadoMascotaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoMascotaService {

    @Autowired
    private EstadoMascotaRepository estadoRepository;

    public List<EstadoMascotaDTO> obtenerTodos() {
        return estadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EstadoMascotaDTO buscarPorId(Integer id) {
        EstadoMascota estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return convertirADTO(estado);
    }

    private EstadoMascota buscarEntidadPorId(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
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

    public void aplicarEfectoJugar(Integer id) {
        EstadoMascota estado = buscarEntidadPorId(id);

        // LÓGICA DE JUEGO ( Cambia en stats)
        estado.setFelicidad(Math.min(100, estado.getFelicidad() + 20));
        estado.setEnergia(Math.max(0, estado.getEnergia() - 15));
        estado.setHambre(Math.max(0, estado.getHambre() - 10)); // El ejercicio da hambre

        verificarLimitesYSalud(estado);
        estadoRepository.save(estado);
    }

    public void aplicarEfectoAlimentar(Integer id, Integer idItem) {

        EstadoMascota estado = buscarEntidadPorId(id);

        // LÓGICA DE ALIMENTACIÓN
        // Al comer, el hambre baja (se acerca a 100, que es "satisfecho")
        estado.setHambre(estado.getHambre() + 25);

        // Da un energía o salud
        estado.setEnergia(estado.getEnergia() + 5);
        estado.setSalud(estado.getSalud() + 10);

        // VALIDACIÓN FINAL (Filtro)
        // verificarLimitesYSalud asegura que si el hambre
        // quedó en 115, baje automáticamente a 100 antes de guardar.
        verificarLimitesYSalud(estado);
        estadoRepository.save(estado);
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
