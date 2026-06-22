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
        return tiendaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Tienda guardarTienda(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public TiendaDTO actualizarNombreTienda(Integer id, String nuevoNombre) {
        // Se verifica que exista la tienda
        Tienda tienda = tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe tienda con ese ID"));

        tienda.setNombreTienda(nuevoNombre);

        Tienda tiendaActualizada = tiendaRepository.save(tienda);

        return convertirADTO(tiendaActualizada);
    }

    public TiendaDTO obtenerPorId(Integer id) {
        return tiendaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("No se encontro la tienda"));
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
