package com.example.mascotav.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.EstadoMascotaDTO;
import com.example.mascotav.DTO.MascotaDTO;
import com.example.mascotav.model.EstadoMascota;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.model.Nivel;
import com.example.mascotav.model.TipoMascota;
import com.example.mascotav.model.Usuario;
import com.example.mascotav.repository.EstadoMascotaRepository;
import com.example.mascotav.repository.MascotaRepository;
import com.example.mascotav.repository.NivelRepository;
import com.example.mascotav.repository.TipoMascotaRepository;
import com.example.mascotav.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private EstadoMascotaService estadoMascotaService;
    
    @Autowired
    private EstadoMascotaRepository estadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    public List<MascotaDTO> obtenerTodos() {
        return mascotaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MascotaDTO buscarPorId(Integer id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        verificarSupervivencia(mascota);

        mascotaRepository.save(mascota);

        return convertirADTO(mascota);
    }

    public String eliminar(Integer id) {
        try {
            Mascota mascota = mascotaRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("¡Imposible eliminar! La mascota no existe."));
            mascotaRepository.delete(mascota);
            return "La mascota " + mascota.getNombre() + " ha sido eutanasiada de la coleccion.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public MascotaDTO crearMascota(Integer userid,Mascota mascota) {
        Usuario usuario = usuarioRepository
        .findById(userid)
        .orElseThrow(() ->
            new RuntimeException("Usuario no encontrado"));  

        TipoMascota tipoMascota = tipoMascotaRepository
        .findById(mascota.getTipoMascota().getId())
        .orElseThrow(() ->
       
        new RuntimeException("Tipo mascota no encontrado"));

        Nivel nivel = nivelRepository
        .findById(mascota.getNivel().getId_nivel())
        .orElseThrow(() ->
            new RuntimeException("Nivel no encontrado"));
       
        mascota.setUsuario(usuario); 
        mascota.setTipoMascota(tipoMascota);
        mascota.setNivel(nivel);

        //guardar datos para generar idmascota
        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota);
        mascotaGuardada.setEstadoMascota(estado);
        
        //actualizo mascota seteo estado
        mascotaRepository.save(mascotaGuardada);
        return convertirADTO(mascotaGuardada);
    }

    public MascotaDTO alimentarMascota(Integer idmascota, Integer idItem) {
        // este aplica el efecto a estado
        Mascota mascota = mascotaRepository.findById(idmascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        if (mascota.getEstadoMascota().getSalud() <= 0) {
            throw new RuntimeException("No puedes alimentar a una mascota que ha fallecido.");
        }
        if (mascota.getEstadoMascota().getHambre() >= 100) {
            throw new RuntimeException(mascota.getNombre() + " ya está completamente satisfecho.");
        }

        estadoMascotaService.aplicarEfectoAlimentar(mascota.getEstadoMascota().getIdEstado(), idItem);

        this.ganarExperiencia(idmascota, 10);

        return buscarPorId(idmascota);
    }

    public MascotaDTO jugarConMascota(Integer idmascota) {
        Mascota mascota = mascotaRepository.findById(idmascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        if (mascota.getEstadoMascota().getSalud() <= 0) {
            throw new RuntimeException("No puedes jugar con la mascota porque murio.");
        }

        if (mascota.getEstadoMascota().getFelicidad() >= 100) {
            throw new RuntimeException(mascota.getNombre() + " ya está completamente feliz.");
        }

        if (mascota.getEstadoMascota().getEnergia() < 15) {
            throw new RuntimeException(mascota.getNombre() + " está demasiado cansado para jugar. No lo molestes.");
        }

        estadoMascotaService.aplicarEfectoJugar(mascota.getEstadoMascota().getIdEstado());

        return buscarPorId(idmascota);
    }

    private void verificarSupervivencia(Mascota mascota) {
        EstadoMascota estado = mascota.getEstadoMascota();

        if (estado.getHambre() <= 0) {
            estado.setHambre(0);
            estado.setSalud(0);
            estado.setFelicidad(0);
            System.out.println("La mascota ha " + mascota.getNombre() + " muerto de hambre por tu culpa.");
        } else {
            // Estado actual si sigue con vida
            System.out.println("--- ESTADO DE MASCOTA ---");
            System.out.println("Nombre: " + mascota.getNombre());
            System.out.println("Hambre: " + estado.getHambre());
            System.out.println("Salud: " + estado.getSalud());
            System.out.println("Felicidad: " + estado.getFelicidad());
            System.out.println("Energia: " + estado.getEnergia());
            System.out.println("------------------------------------------");
        }
    }

    @Transactional
    public MascotaDTO ganarExperiencia(Integer id, Integer puntos) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        int nuevaExp = mascota.getExpActual() + puntos;
        int expNecesaria = 100;

        if (nuevaExp >= expNecesaria) {
            Integer siguienteIdNivel = mascota.getNivel().getId_nivel() + 1;

            Nivel nuevoNivel = nivelRepository.findById(siguienteIdNivel)
                    .orElseThrow(() -> new RuntimeException("¡Felicidades! nivel máximo."));

            mascota.setNivel(nuevoNivel);
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
        masDTO.setNivelActual(mascota.getNivel().getNum_nivel());

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
