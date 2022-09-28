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
