package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.ItemRepository;
import com.example.mascotav.repository.MascotaRepository;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private MascotaService mascotaService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HistorialAccionesService historialAccionesService;
    @Autowired
    private EstadoMascotaService estadoMascotaService;

    public List<InventarioDTO> listarItemdelInventario(Integer iduser){
        List<InventarioDTO> inventItem = new ArrayList<>();
        for(Inventario inv : inventarioRepository.findInventbyUsuario(iduser)){
            inventItem.add(convertirADTO(inv));
        }
        return inventItem;
    }


    public String usarItem(Integer idMascota, Integer idItem){

        Mascota mascota = mascotaRepository.findById(idMascota)
        .orElseThrow(() ->
            new RuntimeException("Mascota no encontrada"));

        Item item = itemRepository.findById(idItem)
        .orElseThrow(() ->
            new RuntimeException("Item no encontrado"));

        Inventario inventario = inventarioRepository
        .findByUsuarioAndItem( // buscar si existen cohincidencias
            mascota.getUsuario().getId(),idItem)
        .orElseThrow(() ->
            new RuntimeException("No tienes este item"));

        if(inventario.getCantidad() <= 0){
        throw new RuntimeException("No hay stock del item");
        }

         // aplicar efecto // separar efecto accion + estado
        estadoMascotaService.aplicarEfecto(mascota, item);
        //comprobar si subio de nivel despues de la accion
        mascotaService.validarSubirDeNivel(mascota);

        //se resta item del inventario
        inventario.setCantidad(inventario.getCantidad() - 1);

        // guardar historial separado
        historialAccionesService.registrarHistorial(mascota, item);

        mascotaRepository.save(mascota);
        inventarioRepository.save(inventario);

        return mascota.getNombre() + " usó " + item.getNombreItem();
    }



    private InventarioDTO convertirADTO (Inventario inv){
        InventarioDTO invDTO = new InventarioDTO();
        invDTO.setId_inven(inv.getId_inven());
        invDTO.setCantidad(inv.getCantidad());
        
        if(inv.getItem() != null){
            invDTO.setItem((inv.getItem()).getIdItem());
        }
        if(inv.getUsuario() != null){
            invDTO.setUsuario((inv.getUsuario()).getId());
        }
        return invDTO;
    }
}
    

