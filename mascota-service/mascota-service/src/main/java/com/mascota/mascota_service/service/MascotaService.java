package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.EstadoMascotaRepository;
import com.mascota.mascota_service.repository.MascotaRepository;
import com.mascota.mascota_service.repository.TipoMascotaRepository;

@Service
public class MascotaService {

    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private EstadoMascotaService estadoMascotaService;
    @Autowired
    private NivelService nivelService;

   /* private Usuario obtenerUsuario(Integer iduser){
        return usuarioRepository
                .findById(iduser)
                .orElseThrow(() ->
                    new RuntimeException("Usuario no encontrado"));
    }
*/ 
    private TipoMascota obtenerTipoMascota(Integer idtipo){
        return tipoMascotaRepository
                .findById(idtipo)
                .orElseThrow(() ->
                    new RuntimeException("Tipo mascota no encontrado"));
    }
    private MascotaDTO guardarMascota(Mascota mascota){
        mascotaRepository.save(mascota);
        return convertirADTO(mascota);
        
    }
    private void inicializarEstado(Mascota mascota){
        // se inicia estado unico de la mascota con el id
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota); 
        // se agrega el estado a Mascota
        mascota.setEstadoMascota(estado);
        // guardo cambios
        mascotaRepository.save(mascota);
    }

    public MascotaDTO crearMascota(Integer userid,Mascota mascota) {
    
       // mascota.setUsuario(obtenerUsuario(userid)); 
        mascota.setTipoMascota(obtenerTipoMascota(mascota.getTipoMascota().getId())); 
        //iniciar nivel al crear mascota con level 1 y expRequerida 10 para subir de ni
        mascota.setNivel(nivelService.iniciarNivel());
        mascota.setExpActual(0); // valor para iniciar experiencia a mascota

        //guardar datos para generar idmascota
        guardarMascota(mascota);
        inicializarEstado(mascota);
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
