CREATE TABLE tienda(
    id_tienda INT AUTO_INCREMENT PRIMARY KEY,
    nom_tienda VARCHAR(20) NOT NULL
) ;

CREATE TABLE tiendaItem(
  id_tienda_item INT AUTO_INCREMENT PRIMARY KEY,
  cooldown INT NOT NULL,
  id_tienda_fk INT NOT NULL,
  id_item_fk INT NOT NULL,
  CONSTRAINT fk_tienda FOREIGN KEY (id_tienda_fk) REFERENCES tienda(id_tienda)
) ;