# mascota_virtual

# Instrucciones endpoints

# IMPORTANTE * Primeros a ejecutar *
## crear tienda
[POST] /api/v1/tienda
{    
    "nombreTienda":"nombretienda"
}
## crear acciones
[POST] /api/v1/accion
{
    
    "nombreAccion":"jugar plei",
    "afectaFelicidad": 15,
    "afectaEnergia": -7,
    "afectaSalud": -5,
    "afectaHambre": -15,
    "afectaExpBase": 20 
    
}

afectaExpBase es la experiencia que ganará la mascota. 

## crear items
[POST] /api/v1/item
{
   
    "nombreItem":"nombreitem",
    "tipoItem": "comida",
    "accion":{
        "idAccion":2
    }
}
## iniciar tipos de mascota
[POST] /api/v1/tipo-mascota/crear

Este endpoint es un metodo que inicia la creacion alatorea de 5 tipos diferentes.

## Agregar Item a la tienda
[POST] /api/v1/tiendaItem/agregarItemTienda

{
    "tienda":{
        "idTienda":1
        },
    "item":{
        "idItem":3
        }
}

# Endpoints fundamentales
## Crear Usuario
[POST] /api/v1/usuario
->
{
    "nombreUsuario":"nombre usuario",
    "fechaCreacion": "dd/mm/yyyy"
}
    
    
## Crear Mascota
[POST] /api/v1/mascota/crear/(IDUSUARIO)
se crea la mascota para ese usuario.
->
    
    "nombre":"mimi",   
    "tipoMascota": {
            "id": 1
        }
Al crear mascota se genera el Estado Mascota automaticamente y se inicia el nivel junto con su experencia.

# Endpoints para INTERACTUAR con item y mascota
## comprar item de la tienda y agregarlo al inventario automaticamente
[POST] /api/v1/tiendaItem/(IDMASCOTA)/comprarItems/(IdITEM)

## ver items comprados en inventario del usuario
[GET] /api/v1/inventario/(IDUSUARIO)

Muestra todos los item del inventario de ese usuario.

## dar item a mascota
[POST] /api/v1/inventario/(IDMASCOTa)/darItem/(IDITEM)

El item es manejado desde Inventario y asi actualiza la cantidad del item en ese inventario. Al queda sin stock en el inventario se debe comprar de nuevo el item.

Al dar el item a la mascota este ganará experiencia y subira de nivel.