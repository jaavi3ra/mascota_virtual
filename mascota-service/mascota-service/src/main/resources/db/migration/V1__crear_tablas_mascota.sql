CREATE TABLE nivel (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    num_nivel INT NOT NULL,
    exp_req INT NOT NULL
);

CREATE TABLE mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    exp_actual INT NOT NULL,

    id_tipo_mascota_fk INT,
    id_user_fk INT NOT NULL,
    id_nivel_fk INT NOT NULL
);

CREATE TABLE estadomascota (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,

    hambre INT NOT NULL,
    felicidad INT NOT NULL,
    energia INT NOT NULL,
    salud INT NOT NULL,

    id_mascota INT NOT NULL UNIQUE /* OneToOne con mascota*/

     /*,CONSTRAINT fk_estado_mascota_mascota
        FOREIGN KEY (id_mascota)
        REFERENCES mascota(id_mascota)
    */  
);

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
CREATE TABLE tipoMascota (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombreTipoMascota VARCHAR(50) NOT NULL
);

ALTER TABLE mascota
ADD CONSTRAINT fk_mascota_tipo
FOREIGN KEY (id_tipo_mascota_fk)
REFERENCES tipomascota(id_tipo_mascota);

ALTER TABLE mascota
ADD CONSTRAINT fk_mascota_nivel
FOREIGN KEY (id_nivel_fk)
REFERENCES nivel(id_nivel);

ALTER TABLE mascota
ADD CONSTRAINT fk_mascota_usuario
FOREIGN KEY (id_user_fk)
REFERENCES usuario(id_usuario);

ALTER TABLE estadomascota
ADD CONSTRAINT fk_estado_mascota
FOREIGN KEY (id_mascota)
REFERENCES mascota(id_mascota);