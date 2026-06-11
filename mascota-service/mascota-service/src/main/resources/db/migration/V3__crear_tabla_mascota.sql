CREATE TABLE mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    exp_actual INT NOT NULL,

    id_tipo_mascota_fk INT,
    id_user_fk INT NOT NULL,
    id_nivel_fk INT NOT NULL

    /*,CONSTRAINT fk_mascota_tipo_mascota
        FOREIGN KEY (id_tipo_mascota_fk)
        REFERENCES tipo_mascota(id_tipo_mascota),

    CONSTRAINT fk_mascota_usuario
        FOREIGN KEY (id_user_fk)
        REFERENCES usuario(id_usuario),

    CONSTRAINT fk_mascota_nivel
        FOREIGN KEY (id_nivel_fk)
        REFERENCES nivel(id_nivel)
    */    
);