package com.example.mascotav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascotav.model.EstadoMascota;
import com.example.mascotav.repository.EstadoMascotaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoMascotaService {

    @Autowired
    private EstadoMascotaRepository estadoRepository;

    public EstadoMascota buscarPorId(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    }

    public void aplicarDesgaste(Integer idEstado) {
        EstadoMascota estado = buscarPorId(idEstado);

        // Regla de Negocio: El hambre baja con el tiempo (se acerca a 0)
        estado.setHambre(Math.max(0, estado.getHambre() - 5));

        // Si tiene hambre (bajo 20), la salud empieza a bajar
        if (estado.getHambre() < 20) {
            estado.setSalud(Math.max(0, estado.getSalud() - 10));
        }

        // Si el hambre llegó a 0, la salud baja a 0 inmediatamente
        if (estado.getHambre() == 0) {
            estado.setSalud(0);
            estado.setFelicidad(0);
        }

        estadoRepository.save(estado);
    }

    public void modificarEnergia(Integer idEstado, int cantidad) {
        EstadoMascota estado = buscarPorId(idEstado);

        // Math.min/max asegura que nunca salga del rango 0-100
        int nuevaEnergia = Math.min(100, Math.max(0, estado.getEnergia() + cantidad));
        estado.setEnergia(nuevaEnergia);

        estadoRepository.save(estado);
    }

}
