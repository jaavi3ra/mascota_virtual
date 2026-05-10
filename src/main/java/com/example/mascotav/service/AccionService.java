package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascotav.DTO.AccionDTO;
import com.example.mascotav.model.Accion;
import com.example.mascotav.repository.AccionRepository;
import com.example.mascotav.repository.HistorialAccionesRepository;

@Service
public class AccionService {
    @Autowired
    private AccionRepository accionRepository;

    @Autowired
    private HistorialAccionesRepository historialAccionesRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private EstadoMascotaService estadoMascotaService;

    public List<AccionDTO> obtenerTodas() {
        return accionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Accion guardar(Accion accion) {
        return accionRepository.save(accion);
    }

    /**
     * Lógica principal: Ejecuta el efecto de un Item sobre la Mascota.
     * Este método conecta tu trabajo con el de tus compañeros.
     */
    public String ejecutarAccionDeItem(Mascota mascota, Item item) {
        
        // 1. Obtenemos la Acción que está vinculada al Item que se usó
        Accion accion = item.getAccion();
        
        if (accion == null) {
            throw new RuntimeException("Este ítem no tiene una acción configurada en la base de datos.");
        }

        // 2. OBTENEMOS EL ESTADO (Uso de declaración básica/explícita)
        // Aquí le pedimos a la Mascota su objeto de EstadoMascota
        EstadoMascota estado = mascota.getEstadoMascota();
        
        // 3. Aplicamos los efectos de TU modelo Accion a los stats de la Mascota
        estado.setHambre(estado.getHambre() + accion.getAfectaHambre());
        estado.setFelicidad(estado.getFelicidad() + accion.getAfectaFelicidad());
        estado.setEnergia(estado.getEnergia() + accion.getAfectaEnergia());
        estado.setSalud(estado.getSalud() + accion.getAfectaSalud());

        // 4. Validamos que los puntos no se pasen de 100 ni bajen de 0
        // Usamos el método que programó tu compañero
        estadoMascotaService.verificarLimitesYSalud(estado); 

        // 5. Registramos el evento en el Historial para trazabilidad
        HistorialAcciones registro = new HistorialAcciones();
        registro.setMascota(mascota);
        registro.setAccion(accion);
        historialRepository.save(registro);

        // 6. Otorgamos la experiencia base definida en tu tabla Accion
        mascota.setExpActual(mascota.getExpActual() + accion.getAfectaExpBase());
        mascotaRepository.save(mascota);

        return "Se ha procesado el ítem '" + item.getNombreItem() + "' con la acción: " + accion.getNombreAccion();
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