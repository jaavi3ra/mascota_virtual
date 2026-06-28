CREATE TABLE item (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    tipo_item VARCHAR(20) NOT NULL
);


CREATE TABLE inventario(
    id_inven INT AUTO_INCREMENT  PRIMARY KEY,
    cantidad INT NOT NULL,
    id_item_FK INT NOT NULL,
    id_usuario_FK INT NOT NULL,
    CONSTRAINT fk_inven_item FOREIGN KEY (id_item_FK) REFERENCES item(id_item)
);
