package com.accion.accion.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.accion.accion.DTO.AccionDTO;
import com.accion.accion.DTO.ItemDTOExterno;
import com.accion.accion.DTO.MascotaDTOExterno;
import com.accion.accion.model.Accion;
import com.accion.accion.model.HistorialAcciones;
import com.accion.accion.repository.AccionRepository;
import com.accion.accion.repository.HistorialAccionesRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AccionService {

    @Autowired
    private AccionRepository accionRepository;

    @Autowired
    private HistorialAccionesRepository historialAccionesRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<AccionDTO> obtenerTodas() {
        log.info("Obteniendo todas las acciones");
        return accionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();

    }

    public Accion guardar(Accion accion) {
        log.info("Guardando nueva accion", accion.getNombreAccion());
        return accionRepository.save(accion);
    }

    public AccionDTO buscarPorId(Integer id) {
        log.info("Buscando accion por ID", id);

        Accion accion = accionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accion con ID " + id + " no encontrada."));
        return convertirADTO(accion);

    }


    // Lógica principal: Conecta con la tienda y las mascotas por red, e inserta el historial local
    public String ejecutarAccionDeItem(Integer idMascota, Integer idItem) {
        
        log.info("Ejecutando accion de item: idItem={}, idMascota={}", idItem, idMascota);

        // 1. Llama al microservicio de Inventario para buscar los datos del ítem
        ItemDTOExterno item = webClientBuilder.build()
                .get()
                .uri("http://inventario-service/api/v1/item/{id}", idItem)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Item no encontrado en la tienda."))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> 
                    Mono.error(new RuntimeException("Error en el servidor de inventario."))
                )
                .bodyToMono(ItemDTOExterno.class)
                .block();

        if (item == null || item.getIdAccionFk() == null) {
            throw new RuntimeException("El item obtenido no posee una accion configurada.");
        }

        // 2. Busca localmente en tu tabla si existe la Accion que viene amarrada a ese Item
        Accion accion = accionRepository.findById(item.getIdAccionFk())
                .orElseThrow(() -> new RuntimeException("La accion vinculada al item no existe localmente"));

        // 3. Llama al microservicio de Mascotas para obtener el nombre del animal usando su ID
        MascotaDTOExterno mascota = webClientBuilder.build()
                .get()
                .uri("http://mascota-service/api/v1/mascota/buscar-pet/{id}", idMascota)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Mascota no encontrada."))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> 
                    Mono.error(new RuntimeException("Error en el servidor de mascotas."))
                )
                .bodyToMono(MascotaDTOExterno.class)
                .block();

        String nombreMascota = (mascota != null) ? mascota.getNombre() : "Desconocido";

        // 4. Guarda el registro del evento en tu tabla local de historial_acciones
        HistorialAcciones registro = new HistorialAcciones();
        registro.setIdMascota(idMascota);
        registro.setAccion(accion);
        registro.setDescripcion(nombreMascota + " uso el item " + item.getNombreItem());
        historialAccionesRepository.save(registro);

        return "Se ha procesado el item '" + item.getNombreItem() + "' con la accion: " + accion.getNombreAccion();
    }

    private AccionDTO convertirADTO(Accion accion) {
        AccionDTO accDTO = new AccionDTO();
        accDTO.setIdAccion(accion.getIdAccion());
        accDTO.setNombreAccion(accion.getNombreAccion());
        accDTO.setAfectaFelicidad(accion.getAfectaFelicidad());
        accDTO.setAfectaEnergia(accion.getAfectaEnergia());
        accDTO.setAfectaSalud(accion.getAfectaSalud());
        accDTO.setAfectaHambre(accion.getAfectaHambre());
        accDTO.setAfectaExpBase(accion.getAfectaExpBase());

        List<String> nombresAccion = new ArrayList<>();
        nombresAccion.add(accion.getNombreAccion());
        accDTO.setNombres_acciones(nombresAccion);

        return accDTO;
    }

}



