package com.example.mascotav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mascotav.model.Usuario;

import feign.Param;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT COUNT(m) FROM Mascota m WHERE m.usuario.id = :idUsuario")
    Integer contarMascotas(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT SUM(m.nivel) FROM Mascota m WHERE m.usuario.id = :idUsuario")
    Integer sumarNivelMascotas(@Param("idUsuario") Integer idUsuario);
}
