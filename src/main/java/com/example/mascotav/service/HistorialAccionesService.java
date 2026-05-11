package com.example.mascotav.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.HistorialAccionesDTO;
import com.example.mascotav.model.HistorialAcciones;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.HistorialAccionesRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistorialAccionesService {

    @Autowired
    private HistorialAccionesRepository historialAccionesRepository;

    public List<HistorialAccionesDTO> obtenerTodos() {
        return historialAccionesRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public HistorialAccionesDTO registrarHistorial(Mascota mascota, Item item){

    HistorialAcciones historial = new HistorialAcciones();

    historial.setMascota(mascota);
    historial.setAccion(item.getAccion());
    historial.setDescripcion(mascota.getNombre() +" usó el item " + item.getNombreItem());

        historialAccionesRepository.save(historial);
        return convertirADTO(historial);
    }

    private HistorialAccionesDTO convertirADTO(HistorialAcciones h) {
        HistorialAccionesDTO hisDTO = new HistorialAccionesDTO();
        hisDTO.setIdHistorial(h.getIdHistorial());

        // Se verifica que exista la mascota
        if(h.getMascota() != null) {
            hisDTO.setNombre_mascota(h.getMascota().getNombre());
        }else {
            hisDTO.setNombre_mascota("desconocido");
        }
        
        // Se verifica la accion
        if(h.getAccion() != null) {
            hisDTO.setNombre_accion(h.getAccion().getNombreAccion());
        }else {
            hisDTO.setNombre_accion("desconocido");
        }

        return hisDTO;
    }
}
