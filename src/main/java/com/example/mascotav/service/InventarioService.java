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

    public List<InventarioDTO> listarItemdelInventario(Integer iduser){
        List<InventarioDTO> inventItem = new ArrayList<>();
        for(Inventario inv : inventarioRepository.findInventbyUsuario(iduser)){
            inventItem.add(convertirADTO(inv));
        }
        return inventItem;
    }

    @Transactional
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

        //aplicarEfecto(mascota, item);
        mascota.setExpActual( mascota.getExpActual() + item.getAccion().getAfectaExpBase());
        

        // guardar cambios
        mascotaRepository.save(mascota);
        mascotaService.validarSubirDeNivel(mascota);
        historialAccionesService.registrarHistorial(mascota, item);

        return Mensaje(mascota, item);
      }

    private String Mensaje(Mascota mascota, Item item){

    return " La Mascota: " + mascota.getNombre() +
           " usó el item '" + item.getNombreItem() +
           "' y activó la acción '" + item.getAccion().getNombreAccion();
           
}

        public EstadoMascota aplicarEfecto(Mascota mascota, Item item){

            EstadoMascota estado = mascota.getEstadoMascota();

        estado.setFelicidad(
             mascota.getEstadoMascota().getFelicidad() + item.getAccion().getAfectaFelicidad()
        );
        estado.setEnergia(
             mascota.getEstadoMascota().getEnergia() + item.getAccion().getAfectaEnergia()
        );
         estado.setHambre(
             mascota.getEstadoMascota().getHambre() + item.getAccion().getAfectaHambre()
        );

       estado.setSalud(
            mascota.getEstadoMascota().getSalud() + item.getAccion().getAfectaSalud()
        );
    
           return  estadoRepository.save(estado);
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
    

