package com.example.Tienda_Gestion.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.TiendaDTO;
import com.example.mascotav.model.Tienda;
import com.example.mascotav.repository.TiendaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<TiendaDTO> obtenerTodas() {
        return tiendaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Tienda guardar(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    // Metodo para cambiar el nombre de la tienda
    public TiendaDTO actualizarNombre(Integer id, String nuevoNombre) {
        // Se verifica que no exista la tienda
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
