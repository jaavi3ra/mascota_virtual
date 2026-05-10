package com.example.mascotav.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mascotav.model.Tienda_item;
import com.example.mascotav.model.Usuario;
import com.example.mascotav.model.Usuario_Tienda_Item;
import com.example.mascotav.repository.InventarioRepository;
import com.example.mascotav.repository.Tienda_itemRepository;
import com.example.mascotav.repository.UsuarioRepository;
import com.example.mascotav.repository.Usuario_Tienda_ItemRepository;
import com.example.mascotav.DTO.Usuario_Tienda_ItemDTO;
import com.example.mascotav.model.Inventario;
import com.example.mascotav.model.Item;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class Usuario_tienda_item_Service {
    @Autowired
    private Usuario_Tienda_ItemRepository ushop_item_repo;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private Tienda_itemRepository tienda_itemRepository;
    @Autowired
    private InventarioRepository inventarioRepository;

    public Usuario_Tienda_Item crearcompra(Integer idusuario, Integer idTiendaItem){

         Usuario usuario = usuarioRepository.findById(idusuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Tienda_item tiendaItem = tienda_itemRepository.findById(idTiendaItem)
            .orElseThrow(() -> new RuntimeException("Item de tienda no encontrado"));
   
        //obtener item de tienda
        Item item = tiendaItem.getItem();

        //si es primera compra
        Usuario_Tienda_Item registro;
        Optional<Usuario_Tienda_Item> optregistro =
            ushop_item_repo.findByUsuarioAndTienda_item(usuario, tiendaItem);

        if(optregistro.isEmpty()){
            registro = new Usuario_Tienda_Item();
        registro.setUsuario(usuario);
        registro.setTienda_item(tiendaItem);
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

        private boolean puedeReclamar(Usuario_Tienda_Item registro,Tienda_item tiendaItem) {

            LocalDateTime proximaReclamacion =
            registro.getUltimaCompra()
                    .plusHours(tiendaItem.getCooldown_segundos());

            return LocalDateTime.now()
            .isAfter(proximaReclamacion);
        }


        private Usuario_Tienda_ItemDTO convertirADTO (Usuario_Tienda_Item eti){ // metodo DTO sin uso
        Usuario_Tienda_ItemDTO etiDTO = new Usuario_Tienda_ItemDTO();
            etiDTO.setId(eti.getId());
            etiDTO.setCooldown(eti.getUltimaCompra());
            if(eti.getUsuario().getId() != null){
               etiDTO.getUsuario().setId(eti.getUsuario().getId());
            }

            if(eti.getTienda_item() != null){
                etiDTO.setItemdelatienda(eti.getTienda_item());
            } 
                return etiDTO;

    }
}
