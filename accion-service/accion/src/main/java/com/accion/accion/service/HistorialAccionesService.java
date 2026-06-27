package com.accion.accion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accion.accion.DTO.HistorialAccionesDTO;
import com.accion.accion.model.Accion;
import com.accion.accion.model.HistorialAcciones;
import com.accion.accion.repository.AccionRepository;
import com.accion.accion.repository.HistorialAccionesRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistorialAccionesService {

    @Autowired
    private HistorialAccionesRepository historialAccionesRepository;

    @Autowired
    private AccionRepository accionRepository;

    public List<HistorialAccionesDTO> obtenerTodos() {
        return historialAccionesRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public HistorialAcciones guardar(Integer idMascota, Accion accion, String descripcion) {
        HistorialAcciones registro = new HistorialAcciones();
        registro.setIdMascota(idMascota);
        registro.setAccion(accion);
        registro.setDescripcion(descripcion);
        
        return historialAccionesRepository.save(registro);
    }

    public HistorialAcciones guardarDesdeExterno(Integer idMascota, Integer idAccion, String descripcion) {
        HistorialAcciones registro = new HistorialAcciones();
        registro.setIdMascota(idMascota);
        registro.setDescripcion(descripcion);

        if (idAccion != null) {
            Accion accion = accionRepository.findById(idAccion).orElse(null);
            registro.setAccion(accion);
        }

        return historialAccionesRepository.save(registro);
    }

    private HistorialAccionesDTO convertirADTO(HistorialAcciones h) {
        HistorialAccionesDTO hisDTO = new HistorialAccionesDTO();
        hisDTO.setIdHistorial(h.getIdHistorial());
        hisDTO.setNombre_mascota("Ver detalle en descripcion");
        if (h.getAccion() != null) {
            hisDTO.setNombre_accion(h.getAccion().getNombreAccion());
        } else {
            hisDTO.setNombre_accion("desconocido");
        }
        return hisDTO;
    }
    
}
