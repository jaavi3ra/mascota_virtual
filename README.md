# mascota_virtual

Proyecto backend desarrollado con Spring Boot y MySQL enfocado en la gestión de mascotas virtuales con mecánicas RPG.

---

# Instrucciones de Endpoints

# IMPORTANTE >:c
## Primeros endpoints a ejecutar

Estos endpoints deben ejecutarse antes de comenzar a utilizar el sistema principal.

---

# Crear Tienda

## Endpoint
```http
[POST] /api/v1/tienda
```

## Body
```json
{
    "nombreTienda":"nombretienda"
}
```

---

# Crear Acciones

## Endpoint
```http
[POST] /api/v1/accion
```

## Body
```json
{
    "nombreAccion":"jugar plei",
    "afectaFelicidad": 15,
    "afectaEnergia": -7,
    "afectaSalud": -5,
    "afectaHambre": -15,
    "afectaExpBase": 20 
}
```

## Descripción
- `afectaExpBase` corresponde a la experiencia que ganará la mascota al realizar la acción.

---

# Crear Items

## Endpoint
```http
[POST] /api/v1/item
```

## Body
```json
{
    "nombreItem":"nombreitem",
    "tipoItem":"comida",
    "accion":{
        "idAccion":2
    }
}
```

---

# Iniciar Tipos de Mascota

## Endpoint
```http
[POST] /api/v1/tipo-mascota/crear
```

## Descripción
Este endpoint ejecuta automáticamente la creación aleatoria de 5 tipos diferentes de mascotas.

---

# Agregar Item a la Tienda

## Endpoint
```http
[POST] /api/v1/tiendaItem/agregarItemTienda
```

## Body
```json
{
    "tienda":{
        "idTienda":1
    },
    "item":{
        "idItem":3
    }
}
```

---

# Endpoints Fundamentales

# Crear Usuario

## Endpoint
```http
[POST] /api/v1/usuario
```

## Body
```json
{
    "nombreUsuario":"nombre usuario",
    "fechaCreacion":"dd/mm/yyyy"
}
```

---

# Crear Mascota

## Endpoint
```http
[POST] /api/v1/mascota/crear/{IDUSUARIO}
```

## Descripción
Crea una mascota asociada al usuario indicado.

Al crear la mascota:
- Se genera automáticamente el `EstadoMascota`
- Se inicializa el nivel
- Se inicia la experiencia de la mascota

## Body
```json
{
    "nombre":"mimi",
    "tipoMascota":{
        "id":1
    }
}
```

---

# Endpoints para Interactuar con Items y Mascotas

# Comprar Item y Agregar al Inventario

## Endpoint
```http
[POST] /api/v1/tiendaItem/{IDMASCOTA}/comprarItems/{IDITEM}
```

## Descripción
Compra un item desde la tienda y lo agrega automáticamente al inventario del usuario dueño de la mascota.

---

# Ver Inventario del Usuario

## Endpoint
```http
[GET] /api/v1/inventario/{IDUSUARIO}
```

## Descripción
Muestra todos los items almacenados en el inventario del usuario.

---

# Dar Item a Mascota

## Endpoint
```http
[POST] /api/v1/inventario/{IDMASCOTA}/darItem/{IDITEM}
```

## Descripción
El item es utilizado desde el inventario del usuario.

Al utilizar un item:
- Se actualiza automáticamente la cantidad disponible en inventario
- Si el item queda sin stock, debe volver a comprarse
- La mascota gana experiencia
- La mascota puede subir de nivel
- Se actualizan los estados de la mascota según la acción asociada al item

---

# Tecnologías Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman
- Git y GitHub

---



## Ejecutar proyecto

```bash
mvn spring-boot:run
```

O ejecutar la clase principal desde el IDE.

---