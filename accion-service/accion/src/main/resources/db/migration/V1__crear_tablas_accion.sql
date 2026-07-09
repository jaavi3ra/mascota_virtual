CREATE TABLE accion (
        id_accion INT AUTO_INCREMENT,
        nombre_accion VARCHAR(100) NOT NULL,
        afecta_felicidad INT NOT NULL,
        afecta_energia INT NOT NULL,
        afecta_salud INT NOT NULL,
        afecta_hambre INT NOT NULL,
        afecta_exp_base INT NOT NULL,
    PRIMARY KEY (id_accion)
);

CREATE TABLE historial_acciones (
        id_historial INT AUTO_INCREMENT,
        descripcion VARCHAR(255),
        id_mascota INT NOT NULL,
        id_accion INT NOT NULL,
        PRIMARY KEY (id_historial),
    CONSTRAINT fk_historial_accion FOREIGN KEY (id_accion) REFERENCES accion(id_accion)
);

