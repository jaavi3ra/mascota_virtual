package com.mascota.mascota_service.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mascota")
public class Mascota {
    
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre debe tener al menos 4 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "exp_actual", nullable = false)
    private Integer expActual;

    // --- RELACIÓN ---
    @ManyToOne
    @JoinColumn(name = "id_tipo_mascota_fk")
    private TipoMascota tipoMascota;

    @Column(name = "id_user_FK", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nivel_FK")
    @EqualsAndHashCode.Exclude
    private Nivel nivel;
    //cascade type ayuda a la creacion de estado automatico
    @OneToOne(mappedBy = "mascota", cascade = CascadeType.ALL)
    private EstadoMascota estadoMascota;

   
}
