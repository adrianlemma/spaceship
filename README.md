# spaceship
> Challenge técnico para [Mindata](https://www.mindata.es/) y [World2Meed](https://www.w2m.travel/es-es/) - API Spaceship

## Características del proyecto
La aplicación se encarga de la gestion de la base de datos H2 (en memoria)

La misma cuenta con:
* Authenticación Básica implementada con [Spring Security](https://spring.io/projects/spring-security)
* Una base de datos en memoria implementada con [H2](https://www.h2database.com/html/main.html)
* Una cola de mensajería [RabbitMQ](https://www.rabbitmq.com/) que solo se usa para logs de error
* Documentacion de la API con [Swagger](https://swagger.io/)

> ### Tambinen puede probar la aplicacion a travez del front-end [spaceship-front](https://adrianlemma.github.io)

---

## Seguridad
Se implementa la Autenticación Basica de **Spring Security** con 2 usuarios a modo de ejemplo practico

### Usuarios para realizar pruebas
* #### ROL USER
  * Username: **user**
  * Password: **user**
* #### ROL ADMIN
    * Username: **admin**
    * Password: **admin**

> Todos los endpoints del método **GET** no se autentican, permitiedo a cualquier usuario consultar información. 

> Los endpoints de los métodos **POST** y **PUT**  requieres un usuario con rol **"USER"** o **"ADMIN"**

> El endpoint del método **DELETE** requiere u usuario con rol **"ADMIN"**

---

## Base de datos

Se utiliza la base de datos H2 en memoria, la misma presenta el siguiente esquema:
```
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    SPACESHIP_NAME VARCHAR(255) NOT NULL,
    TV_PROGRAM VARCHAR(255) NOT NULL,
    CAPACITY INT,
    LAST_UPDATE TIMESTAMP,
    CONSTRAINT IDX_SPCACECHIP_UNQ UNIQUE (SPACESHIP_NAME, TV_PROGRAM)
```
La misma se inicializa junto a la aplicacion, cargando 50 registros de prueba iniciales.
A tal fin, se utilizan los scripts **schema.sql** y **data.sql**

El campo **LAST_UPDATE** se autocompleta con la fecha actual cada vez que se crea o actualiza un registro

---

## Cola de mensajería MQ
Se implementa **RabbitMQ** como broker de mensajería y por cuestiones de practicidad se utiliza la plataforma [CloudMQP](https://customer.cloudamqp.com/instance)

La función de la cola de mensajería esgenerar un log cuando la aplicación responde un error

* ### Productor de mensajes
> En el controlader de excepciones se encuentra implementado el productor de mensajes de RabbitMQ, el mismo envía la respuesta con código y mensaje de error

* ### Consumidor de mensajes
> La aplicacion tiene implementado un Listener la cola **error_log_queue** de mensajes y al recibir un mensaje genera una linea de log

**Ejemplo de la linea de log generada**

``Se respondio un error - code: [error_code] - message: [error_message]``

---

## Otras características
* ### Aspect
> Se implementa un **@Aspect** para generar una linea de log cada vez que un usuario quiere consultar una Nave Espacial con un **ID** menor a cero

**Ejemplo del log**

``Se intento consultar una nave con un id negativo - [id]: {-1}``

* ### Swagger
> Se omplementa documentación de la aplicación con SwaggerUI

Puede acceder a la documentación Swagger aquí: [Documentación Swagger](https://spaceship-526259401712.us-central1.run.app/swagger-ui/index.html)

---

## Spaceship API
La aplicación consta de seis endpoints dedicados a la gestion de la base de datos de Naves Espaciales de programas de TV

### Método GET

* ### GET [hostname]/spaceships
  * Parametro: **page** [opcional] ``indica el numero de página a consultar``
  * Parametro: **size** [opcional] ``indica el tamaño de la página a consultar``
> Consulta todas las Naves Espaciales con paginación

* ### GET [hostname]/spaceships/{id}
    * Variable: **{id}** ``indica el ID por el que se realizará la búsqueda``
> Consulta una Nave Espacia por **ID**

* ### GET [hostname]/spaceships/by-name/{namePart}
  * Parametro: **page** [opcional] ``indica el numero de página a consultar``
  * Parametro: **size** [opcional] ``indica el tamaño de la página a consultar``
> Consulta todas las Naves Espaciales que contengan en el Nombre, el String recibido en el parametro **namePart**, con paginación

### Método POST

* ### POST [hostname]/spaceships
  * Request Body: ``Json Request"``
    * Request Field: **spaceship_name** ``string - obligatorio``
    * Request Field: **tv_program** ``string - obligatorio``
    * Request Field: **capacity** ``integer - no obligatorio``
> Almacena una nueva Nave Espacial en la base de datos, siempre que no se repitan el nombre y el programa de TV. Este endpoint requiere usuario con rol **USER** o **ADMIN**

### Método PUT

* ### PUT [hostname]/spaceships/{id}
  * Variable: **id** ``indica el ID de la Nave que se actualizará``
  * Request Body: ``Json Request"``
      * Request Field: **spaceship_name** ``string - obligatorio``
      * Request Field: **tv_program** ``string - obligatorio``
      * Request Field: **capacity** ``integer - no obligatorio``
> Actualiza una nueva Nave Espacial de la base de datos, siempre que no se repitan el nombre y el programa de TV. Este endpoint requiere usuario con rol **USER** o **ADMIN**

### Método DELETE

* ### DELETE [hostname]/spaceships/{id}
    * Variable: **id** ``indica el ID de la Nave que se actualizará``
> Elimina una nueva Nave Espacial de la base de datos. Este endpoint requiere usuario con rol **ADMIN**

### Ejemplo de Request [POST | PUT]
```
{
    "spaceship_name": "X-Wing Espacial",
    "tv_program": "X-Men",
    "capacity": 10
}
```

### Ejemplo de response simple [POST | PUT | GET]
```
{
    "id": 1,
    "spaceship_name": "X-Wing Espacial",
    "tv_program": "X-Men",
    "capacity": 10,
    "last_update": "2024-02-03T10:00:00"
}
```

### Ejemplo de response multiple [GET]
```
{
    "content": [
        {
            "id": 1,
            "spaceship_name": "Millennium Falcon",
            "tv_program": "Star Wars",
            "capacity": 6,
            "last_update": "2024-02-03T10:00:00"
        },
        {
            "id": 2,
            "spaceship_name": "Enterprise",
            "tv_program": "Star Trek",
            "capacity": 1000,
            "last_update": "2024-02-03T11:00:00"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 2,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalElements": 50,
    "totalPages": 25,
    "first": true,
    "size": 2,
    "number": 0,
    "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
    },
    "numberOfElements": 2,
    "empty": false
}
```

### Ejemplo de response de error [GET | POST | PUT | DELETE]
```
{
    "error_code": "ERR_001",
    "error_message": "No se encontró nave espacial con id: 123"
}
```
