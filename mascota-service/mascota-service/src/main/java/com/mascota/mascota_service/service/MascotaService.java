package com.mascota.mascota_service.service;

import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.mascota.mascota_service.DTO.EstadoMascotaDTO;
import com.mascota.mascota_service.DTO.MascotaDTO;
import com.mascota.mascota_service.DTO.UsuarioDTOExterno;
import com.mascota.mascota_service.model.EstadoMascota;
import com.mascota.mascota_service.model.Mascota;
import com.mascota.mascota_service.model.TipoMascota;
import com.mascota.mascota_service.repository.MascotaRepository;
import com.mascota.mascota_service.repository.TipoMascotaRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
    private WebClient.Builder webClientBuilder;

   private Integer obtenerUsuario(Integer iduser){
    // falta logs try
        return webClientBuilder.build()
                .get()
                .uri("http://usuario-service/api/v1/usuario/buscar-iduser" + iduser)
                .retrieve()
                // Manejo de errores 4xx o 5xx del microservicio externo
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Usuario no encontrado."))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> 
                    Mono.error(new RuntimeException("Error en el servidor de usuarios"))
                )
                .bodyToMono(UsuarioDTOExterno.class)
                .map(UsuarioDTOExterno::getIdUsuario)
                .block();
    }   

    private TipoMascota obtenerTipoMascota(Integer idtipo){
        // falta log try
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
        return null;
       }
       
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
