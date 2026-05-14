package com.example.mascotav.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.DTO.InventarioDTO;
import com.example.mascotav.model.EstadoMascota;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import com.example.mascotav.model.Mascota;
import com.example.mascotav.repository.EstadoMascotaRepository;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.ItemRepository;
import com.example.mascotav.repository.MascotaRepository;
import jakarta.transaction.Transactional;

@Transactional
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
    private EstadoMascotaRepository estadoRepository;
    @Autowired
    private EstadoMascotaService estadoserService;

    public List<InventarioDTO> listarItemdelInventario(Integer iduser){
        List<InventarioDTO> inventItem = new ArrayList<>();
        for(Inventario inv : inventarioRepository.findInventbyUsuario(iduser)){
            inventItem.add(convertirADTO(inv));
        }
        return inventItem;
    }

    
    public String usarItem(Integer idMascota, Integer idItem){

        Mascota mascota = mascotaRepository.findById(idMascota)
        .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Item item = itemRepository.findById(idItem)
        .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        Inventario inventario = inventarioRepository
        .findByUsuarioAndItem(mascota.getUsuario().getId(), idItem)
        .orElseThrow(() -> new RuntimeException("No tienes este item"));

        if(inventario.getCantidad() <= 0){
        throw new RuntimeException("No hay stock del item");
        }

        inventario.setCantidad(inventario.getCantidad() - 1);
        inventarioRepository.save(inventario);

        mascota.setExpActual( mascota.getExpActual() + item.getAccion().getAfectaExpBase());
        
        // guardar cambios
        mascotaRepository.save(mascota);
        estadoserService.editarEstado(mascota, item);   
        historialAccionesService.registrarHistorial(mascota, item);

        return Mensaje(mascota, item);
      }

    private String Mensaje(Mascota mascota, Item item){

    return " La Mascota: " + mascota.getNombre() +
           "\n\tUsó el item :" + item.getNombreItem() +
           "\n\tActivó la acción :" + item.getAccion().getNombreAccion()+
           "\n\t"+ mascotaService.validarSubirDeNivel(mascota);
           
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
    

