CREATE TABLE evolucion (
    id_evo INT AUTO_INCREMENT PRIMARY KEY,
    nom_evo VARCHAR(10) NOT NULL,
    nivel_evo INT NOT NULL,

    id_tipo_mascota_fk INT NOT NULL

   /* ,CONSTRAINT fk_evolucion_tipo_mascota
        FOREIGN KEY (id_tipo_mascota_fk)
        REFERENCES tipo_mascota(id_tipo_mascota)
   */
);