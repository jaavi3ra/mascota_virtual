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