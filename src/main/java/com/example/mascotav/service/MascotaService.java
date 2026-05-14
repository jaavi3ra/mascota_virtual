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
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota);   
        mascota.setEstadoMascota(estado);

        //actualizo mascota seteo estado
        mascotaRepository.save(mascota);
        return convertirADTO(mascota);
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

   
    public String validarSubirDeNivel(Mascota mascota) {
        
        int exp = mascota.getExpActual();
        int expNecesaria = mascota.getNivel().getExp_req();
        Nivel nivel = mascota.getNivel();

        if (exp >= expNecesaria) {
            Integer nuevoNivel = nivel.getNum_nivel() + 1;

            nivel.setNum_nivel(nuevoNivel);
            mascota.setExpActual(exp - expNecesaria); // con exp=10 y expNecesaria=10 → queda en 0
            nivelRepository.save(nivel);
            mascotaRepository.save(mascota);
           
           return "¡Felicidades! Tu mascota subió al nivel " + nuevoNivel;
        }     
        return "Falta " + (expNecesaria - exp) + " exp para subir de nivel";
        
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
