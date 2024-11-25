# Barberia

## Introducción

Cuando un recurso es escaso y hay varios hilos de ejecución que lo quieren utilizar, se hace necesario regular el acceso al mismo.

Por otro lado, cuando se trata de atender a un cierto nivel de demanda, se hace necesario atender a la escalabilidad del sistema. Con algunos recursos la forma de escalar puede ser horizontal (aumentar el número de recursos según sea necesario). Pero con otros la forma de escalar está limitada a ser vertical (los recursos han de trabajar más rápido según sea necesario).


## Tareas a realizar

Preparar un módulo principal, `Barberia`, que se encarga de inicializar y lanzar lo que sea necesario.

Según llegan (aleatoriamente) los `Cliente`s a la barberia, pasan a una `SalaDeEspera` donde pueden estar un máximo de AFORO_DE_LA_SALA_DE_ESPERA a la vez. Si no hay sitio en la sala de espera, el cliente se va a dar una vuelta y vuelve más tarde (es decir, su hilo se bloquea hasta que haya sitio libre en la sala de espera).

Un `Barbero` se encarga de ir atendiendo a los clientes, uno a uno. Tarda un cierto tiempo aleatorio con cada cliente, comprendido ente MIN_TIEMPO_PROCESO  y MAX_TIEMPO_PROCESO.


### Puntos a comprobar

nota: Como en todo sistema concurrente/paralelo/distribuido, no hay ninguna garantía de determinismo. Es necesario hacer las comprobaciones estadísticamente ejecutando cada caso varias veces (cuantas más, mejor) y usando medias de las distintas ejecuciones.

¿Cómo afecta tener una sala de espera más o menos grande?

¿Cómo afecta tener un barbero más o menos rápido?

¿Qué sucede cuando hay picos de decenas o cientos o miles de clientes? ¿Cómo va escalando el tiempo de atención a cada cliente? (El tiempo total entre ir a la barberia y salir de ella con la barba arreglada.) (Es decir, entre que se arranca y se completa el hilo de ese cliente.)

nota: Para este tipo de análisis, es necesario eliminar la aleatoriedad en el tiempo con cada cliente; poner un tiempo fijo para todos.



## Tareas extras (opcionales)

Implementar varios barberos trabajando.

¿Cómo se coordinan entre sí los clientes y los barberos?

¿Cómo afecta tener más o menos barberos trabajando a la vez?
