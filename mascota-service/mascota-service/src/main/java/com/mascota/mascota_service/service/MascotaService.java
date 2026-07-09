package com.mascota.mascota_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.MascotaRepository;
import com.mascota.mascota_service.repository.TipoMascotaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j // anotacion de lombok para log.error()
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
    @Autowired
    private UsuarioClientService usuarioClientService;


    public MascotaDTO obtenerMascotaId(Integer idpet){
        try{
            log.info("Obteniendo mascota...");
            Mascota mascota = mascotaRepository
                .findById(idpet)
                .orElseThrow(() ->
                    new RuntimeException("mascota no encontrado"));
             return convertirADTO(mascota);
        }catch(Exception e){
            log.error("error al obtener mascota: ",e);
            throw new RuntimeException("No se pudo obtener la mascota", e);
        }
       
    }
   private Integer obtenerUsuario(Integer iduser){
        try{
            log.info("Obteniendo usuario...");
            return usuarioClientService.obtenerUsuario(iduser);
        }catch(Exception e){
            log.error("No se pudo obtener usuario: ", e);
            throw new RuntimeException("No se pudo obtener el usuario", e);
        }
       
    }   

    private TipoMascota obtenerTipoMascota(Integer idtipo){
        try{
            log.info("Obteniendo tipomascota...");
             return tipoMascotaRepository
                .findById(idtipo)
                .orElseThrow(() ->
                    new RuntimeException("Tipo mascota no encontrado"));
        }catch(Exception e){
            log.error("error al obtener tipo mascota: ",e);
            throw new RuntimeException("No se pudo obtener el tipo de mascota", e);
        }
       
    }

    public MascotaDTO guardarMascota(Mascota mascota){
        mascotaRepository.save(mascota);
        log.info("Mascota ingresada.");
        return convertirADTO(mascota);
        
    }
    private void inicializarEstado(Mascota mascota){
        // se inicia estado unico de la mascota con el id
        
        EstadoMascota estado = estadoMascotaService.iniciarEstado(mascota); 
        // se agrega el estado a Mascota
        if(estado != null){
        mascota.setEstadoMascota(estado);
        // guardo cambios
        mascotaRepository.save(mascota);
        log.info("Estado mascota creada.");
        }else{
           log.error("No se pudo crear estado ",estado); 
        }
        
        
    }

    public MascotaDTO actualizarExpMascota(Mascota mascota, Integer afectaExpBase){
       try{
         log.info("actualizando exp mascota...");
        mascota.setExpActual( mascota.getExpActual() + afectaExpBase);
        mascotaRepository.save(mascota);
        return convertirADTO(mascota);
       }catch(Exception e){
        log.error("no se pudo actualizar exp: ", e);
        throw new RuntimeException("No se pudo actualizar la experiencia", e);
       }
        
    }

    public MascotaDTO crearMascota(Integer userid,String nombre, Integer idtipo) {
       try{
        log.info("Creando mascota...");
         Mascota mascota = new Mascota();

        mascota.setNombre(nombre);
        mascota.setUsuario(obtenerUsuario(userid)); 
        mascota.setTipoMascota(obtenerTipoMascota(idtipo)); 
        //iniciar nivel al crear mascota con level 1 y expRequerida 10 para subir de ni
        mascota.setNivel(nivelService.iniciarNivel());
        mascota.setExpActual(0); // valor para iniciar experiencia a mascota

        //guardar datos para generar idmascota
        guardarMascota(mascota);
        inicializarEstado(mascota);
        log.info("mascota creada.");
        return convertirADTO(mascota);
       }catch(Exception e) {
        log.error("No se pudo crear mascota, error:",e);
        throw new RuntimeException("No se pudo crear la mascota", e);
       }
       
    }

    private MascotaDTO convertirADTO(Mascota mascota) {
        MascotaDTO masDTO = new MascotaDTO();

        masDTO.setIdMascota(mascota.getIdMascota());
        masDTO.setNombre(mascota.getNombre());
        masDTO.setNivelActual(mascota.getNivel().getNum_nivel());
        masDTO.setIdUsuarioFk(mascota.getUsuario());

        if (mascota.getTipoMascota() != null) {
            masDTO.setTipoMascota(mascota.getTipoMascota().getId());
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
