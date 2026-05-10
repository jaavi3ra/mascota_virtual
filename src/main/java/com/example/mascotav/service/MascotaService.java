package com.example.mascotav.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascotav.DTO.EstadoMascotaDTO;
import com.example.mascotav.DTO.MascotaDTO;
import com.example.mascotav.model.EstadoMascota;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.model.Nivel;
import com.example.mascotav.repository.EstadoMascotaRepository;
import com.example.mascotav.repository.MascotaRepository;
import com.example.mascotav.repository.NivelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private NivelRepository nivelRepository;

    public List<MascotaDTO> obtenerTodos() {
        return mascotaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MascotaDTO buscarPorId(Integer id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        return convertirADTO(mascota);
    }

    public String eliminar(Integer id) {
        try {
            Mascota mascota = mascotaRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("¡Imposible eliminar! La mascota con ID " + id + " no existe."));
            mascotaRepository.delete(mascota);
            return "La mascota " + mascota.getNombre() + " ha sido borrada de la coleccion.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Mascota guardarMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    // si usuario tiene mas de 2 mascota y aplicar efecto por mascota
    public MascotaDTO alimentarMascota(Integer idmascota, Integer idItem) { // pasar id de item, item lleva a accion y
                                                                            // este aplica el efecto a estado
        Mascota mascota = mascotaRepository.findById(idmascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        EstadoMascota estado = mascota.getEstado();
        // llamar accion por id
        if (estado.getSalud() <= 0) {
            throw new RuntimeException("No puedes alimentar a una mascota que ha fallecido.");
        }
        if (estado.getHambre() >= 100) {
            throw new RuntimeException(mascota.getNombre() + " ya está completamente satisfecho.");
        }

        // agregar edit a estado mascota por id
        mascotaRepository.save(estado); // ????
        return convertirADTO(estado);// ??
    } // tiene que afectar a estado_mascosta dependiendo de la accion

    // separara acciones jugarMascota(id)

    private void verificarSupervivencia(Mascota mascota) {
        EstadoMascota estado = mascota.getEstado();

        if (estado.getHambre() <= 0) {
            estado.setHambre(0);
            estado.setSalud(0);
            estado.setFelicidad(0);
            System.out.println("La mascota ha muerto de hambre.");
        }
        // agregar else si esta vivo muestra estado de la esa mascota por id de mascota
    }

    @Transactional
    public MascotaDTO ganarExperiencia(Integer id, Integer puntos) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        int nuevaExp = mascota.getExpActual() + puntos;
        int expNecesaria = 100; // Esto podría venir de tu tabla 'Nivel'

        // Lógica de Negocio: Subir de nivel si alcanza la experiencia
        if (nuevaExp >= expNecesaria) {
            // Calculamos el ID del siguiente nivel
            Integer siguienteId = mascota.getNivelMascota().getId_nivel() + 1;

            // Buscamos el objeto Nivel real para persistirlo
            Nivel nuevoNivel = nivelRepository.findById(siguienteId)
                    .orElseThrow(() -> new RuntimeException("¡Felicidades! Alcanzaste el nivel máximo."));

            mascota.setNivelMascota(nuevoNivel); // Asignamos el objeto, no el número
            mascota.setExpActual(nuevaExp - expNecesaria);
        } else {
            mascota.setExpActual(nuevaExp);
        }

        mascotaRepository.save(mascota);
        return convertirADTO(mascota);
    }

    private MascotaDTO convertirADTO(Mascota mascota) {
        MascotaDTO masDTO = new MascotaDTO();

        masDTO.setIdMascota(mascota.getIdMascota());
        masDTO.setNombre(mascota.getNombre());
        masDTO.setNivelActual(mascota.getNivel());

        if (mascota.getTipoMascota() != null) {
            masDTO.setTipoMascota(mascota.getTipoMascota().getNombreTipoMascota());
        }
        if (mascota.getEstadoMascota() != null) {
            EstadoMascotaDTO estDTO = new EstadoMascotaDTO();

            estDTO.setIdEstadoMascota(mascota.getEstadoMascota().getIdEstado());
            estDTO.setHambre(mascota.getEstadoMascota().getHambre());
            estDTO.setFelicidad(mascota.getEstadoMascota().getFelicidad());
            estDTO.setEnergia(mascota.getEstadoMascota().getEnergia());
            estDTO.setSalud(mascota.getEstadoMascota().getSalud());

            masDTO.setEstado(estDTO);
        }
        return masDTO;
    }

}
