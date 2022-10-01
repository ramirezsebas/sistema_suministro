# Tarea 3 - Sistemas Distribuidas - Proyectos a desarrollar e implementar

## Sistema Suministros

### Integrantes

1. Fransisco Pintos
2. Matias Ramirez
3. Matias Sosa
4. Matias Sanchez

Cada grupo deberá crear y desarrollar los siguientes proyectos:

1. Un proyecto para el Sistema Departamental de Suministros de NIS (Número de identificación de suministro)
Debe ofrecer servicios sobre el protocolo según su grupo de trabajo.
Debe contar con la información de los suministros del departamento.
Debe contar con una base de datos en memoria (variables globales) o persistente de los suministros del departamento.
Las operaciones que se debe soportar (tipo_operacion) están en el gráfico.
Adicionalmente el servidor deberá guardar un registro de las operaciones realizadas en un log.
El log puede ser un archivo, una estructura almacenada en memoria ó una tabla en base de datos.
Debe contener: fecha-hora, origen (ip:puerto), destino (ip:puerto), tipo_operacion.

2. Un proyecto Cliente que será cada NIS
Debe ser una aplicación cliente consola (standalone) o con interfaz gráfica que realice las operaciones ofrecidas por el servidor
Debe implementar los servicios del Servidor Departamental.

## Servicios Implementados

### Servicios con UDP

1. Registrar Consumo

El cliente podra registar su consumo y cargarlo en la BD.
El cliente necesita enviar las siguientes informaciones:

- NIS (ID)
- Tipo de Operacion = 1
- Cantidad de Consumo de NIS

Por lo tanto, el Request quedaria de la siguiente manera:

```json
{
    "tipoOperacion":1,
    "nis":"1",
    "consumo":123432
}
```

Una vez recibido el request del cliente, el servidor va a realizar lo siguiente:

1. Buscar si el NIS que se paso existe
2. En caso de que exista va a traer el suministro, actualizar su valor de consumo y retornar nis del suministro que se actualizo con el nuevo consumo
3. Sino, avisarle al cliente que no existe su NIS en la BD

Por lo tanto en el caso que exista el suministro el Response seria:

```json
{
    "tipoOperacion":1,
    "estado":0,
    "mensaje":"OK",
    "data":{
        "nis":"1",
        "consumo":123432
    }    
}
```

Por lo tanto en el caso que no exista el suministro el Response seria:

```json
{"estado":0,"tipoOperacion":1,"mensaje":"OK","dato":"No se pudo registrar el consumo, no existe el suministro: 112121"}
```

2. Informar Conectividad

El cliente podra verificar que su NIS este conectado al servidor
El cliente necesita enviar las siguientes informaciones:

- NIS (ID)
- Tipo de Operacion = 2

Por lo tanto, el Request quedaria de la siguiente manera:

```json
{
    "tipoOperacion":2,
    "nis":"1"
}
```

Una vez recibido el request del cliente, el servidor va a realizar lo siguiente:

1. Buscar si el NIS que se paso existe
2. En caso de que exista va a traer el suministro, va a avisarle al usuario que si esta conectado
3. Sino, avisarle al cliente que no existe su NIS en la BD por lo tanto no esta conectado

Por lo tanto el Response seria:

```json
{"estado":0,"tipoOperacion":2,"mensaje":"OK","dato":"El NIS: 1231331 no esta conectado"}
```
