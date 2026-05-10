package com.example.mascotav.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.model.TiendaItem;
import com.example.mascotav.model.Usuario;
import com.example.mascotav.model.UsuarioTiendaItem;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.TiendaItemRepository;
import com.example.mascotav.repository.UsuarioRepository;
import com.example.mascotav.repository.UsuarioTiendaItemRepository;
import com.example.mascotav.DTO.UsuarioTiendaItemDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class Usuario_tienda_item_Service {
    @Autowired
    private UsuarioTiendaItemRepository ushop_item_repo;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TiendaItemRepository tienda_itemRepository;
    @Autowired
    private InventarioRepository inventarioRepository;

    public UsuarioTiendaItem crearcompra(Integer idusuario, Integer idTiendaItem){

         Usuario usuario = usuarioRepository.findById(idusuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TiendaItem tiendaItem = tienda_itemRepository.findById(idTiendaItem)
            .orElseThrow(() -> new RuntimeException("Item de tienda no encontrado"));
   
        //obtener item de tienda
        Item item = tiendaItem.getItem();

        //si es primera compra
        UsuarioTiendaItem registro;
        Optional<UsuarioTiendaItem> optregistro =
            ushop_item_repo.findByUsuarioAndTiendaItem(usuario, tiendaItem);

        if(optregistro.isEmpty()){
            registro = new UsuarioTiendaItem();
        registro.setUsuario(usuario);
        registro.setTiendaItem(tiendaItem);
        registro.setUltimaCompra(LocalDateTime.now());
        }else{
            registro = optregistro.get();
        }
      
        // agregar o aumentar item a inventario
         Optional<Inventario> optInventario =
            inventarioRepository.findByUsuarioAndItem(idusuario, item.getIdItem());
            //si está aumenta cantidad
            if(!optInventario.isEmpty()){
                //aumentar cantidad
                Inventario inventario = optInventario.get(); //agrega datos encontrados
                if (!puedeReclamar(registro, tiendaItem)) {
                    throw new RuntimeException("Cooldown activo, aun no puede comprar este item.");
                    }
                inventario.setCantidad(inventario.getCantidad() + 1);
                inventarioRepository.save(inventario); //se guarda cambios en inventario
            }else{
                //crear nuevo item a inventario
                Inventario nuevoInventario = new Inventario();

                nuevoInventario.setUsuario(usuario);
                nuevoInventario.setItem(item);
                nuevoInventario.setCantidad(1);

                inventarioRepository.save(nuevoInventario);

            }
            return ushop_item_repo.save(registro);

    }

        private boolean puedeReclamar(UsuarioTiendaItem registro,TiendaItem tiendaItem) {

            LocalDateTime proximaReclamacion =
            registro.getUltimaCompra()
                    .plusHours(tiendaItem.getCooldown_segundos());

            return LocalDateTime.now()
            .isAfter(proximaReclamacion);
        }


        private UsuarioTiendaItemDTO convertirADTO (UsuarioTiendaItem eti){ // metodo DTO sin uso
        UsuarioTiendaItemDTO etiDTO = new UsuarioTiendaItemDTO();
            etiDTO.setId(eti.getId());
            etiDTO.setCooldown(eti.getUltimaCompra());
            if(eti.getUsuario().getId() != null){
               etiDTO.getUsuario().setId(eti.getUsuario().getId());
            }

            if(eti.getTiendaItem() != null){
                etiDTO.setItemdelatienda(eti.getTiendaItem());
            } 
                return etiDTO;

    }
}
