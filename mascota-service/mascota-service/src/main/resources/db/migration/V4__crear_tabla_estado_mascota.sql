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