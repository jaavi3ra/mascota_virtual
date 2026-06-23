package com.example.Tienda_Gestion.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Tienda_Gestion.Model.Tienda;
import com.example.Tienda_Gestion.DTO.TiendaDTO;
import com.example.Tienda_Gestion.Repository.TiendaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<TiendaDTO> listarTodasTiendas() {
        log.info("Listando tiendas");

        List<TiendaDTO> tiendas = tiendaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();

        log.info("Tiendas listadas correctamente");
        return tiendas;
    }

    public Tienda guardarTienda(Tienda tienda) {
        log.info("Guardando tienda");

        Tienda tiendaGuardada = tiendaRepository.save(tienda);

        log.info("Tienda guardada correctamente");
        return tiendaGuardada;
    }

    public TiendaDTO actualizarNombreTienda(Integer id, String nuevoNombre) {
        log.info("Actualizando nombre de tienda");

        // Se verifica que exista la tienda
        Tienda tienda = tiendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se encontró la tienda");
                    return new RuntimeException("No existe tienda con ese ID");
                });

        tienda.setNombreTienda(nuevoNombre);

        Tienda tiendaActualizada = tiendaRepository.save(tienda);

        log.info("Nombre de tienda actualizado correctamente");
        return convertirADTO(tiendaActualizada);
    }

    public TiendaDTO obtenerPorId(Integer id) {
        log.info("Buscando tienda");

        return tiendaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.error("No se encontró la tienda");
                    return new RuntimeException("No se encontró la tienda");
                });
    }

    private TiendaDTO convertirADTO(Tienda tienda) {
        TiendaDTO tieDTO = new TiendaDTO();
        tieDTO.setIdTienda(tienda.getIdTienda());
        tieDTO.setNombreTienda(tienda.getNombreTienda());

        List<String> items = new ArrayList<>();
        tieDTO.setItemsDelatienda(items);

        return tieDTO;
    }
}
