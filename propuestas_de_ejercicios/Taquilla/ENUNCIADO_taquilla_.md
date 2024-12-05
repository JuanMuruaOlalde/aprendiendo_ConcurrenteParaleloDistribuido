# Taquillas

## Introducción

Operaciones que son simples y claras en una ejecución secuencial, pueden llegar a ser problemáticas en una ejecución concurrente/paralela/distribuida.

Y la cosa se complica más si aparece la "escala Internet": muchas personas intentando usar el servicio en corto espacio de tiempo (es decir, muchas peticiones por segundo). Por ejemplo:

- En un gran concierto, miles de personas intentan comprar entrada en la primera hora en que se abre la taquilla, para conseguir las mejores localidades.

- En un cine, decenas de personas intentan comprar entrada en los últimos 15 minutos antes de la sesión.

En esos dos casos, en la época pre-Internet, la problemática se visualizaba claramente en la longitud de la cola física que se formaba en las taquillas. Pero en un sistema web cada persona está a solas con su móvil u ordenador, no siendo consciente de la avalancha que está teniendo que soportar la taquilla; y espera un servicio tranquilo, como si le estuvieran atendiendo a ella sola.

Como botón de muestra, no se espera que esos asientos que está marcando en la pantalla y tarda tres minutos en decidirse; haya otras muchas personas haciendo lo mismo en ese justo momento; y asientos que estaban libres hace medio segundo ya no lo estén cuando intenta volver a ellos dos clics después.


## Tareas a realizar

Modelar un `Cine` con un patio de `Butaca`s

Habrá varias copias de dicho patio de butacas, una por cada sesión.

Cada `Butaca` tiene:

- Un identificador único `id`, un texto indicando "fila::numero".

- Una `ubicación`, indicando su distancia 2D desde un origen en la parte inferior-central de la pantalla. La distancia x será los metros cerca/lejos que esté y la distancia y será los metros derecha(+)/izquierda(-) que esté (visto por una persona sentada en la butaca, mirando a la pantalla).

- Un booleano `estaLibre`.

Pensando en que estamos en una sesión concreta (una copia concreta del patio de butacas). Modelar el proceso para permitir a una hebra `Cliente`: 

- Recibir una lista de las butacas libres.

- Seleccionar unas butacas (la hebra escogerá un cierto número de butacas aleatoriamente, tardando un cierto tiempo aleatorio en seleccionarlas).

- Pagar (llamar a una función que tarde un cierto tiempo aleatorio en responder)

- Marcar esas butacas como ocupadas.

- Emitir las entradas (un UUID, Universally Unique IDentifier).


### Puntos a comprobar

#### A

Con un solo cliente, la cosa es sencilla. Pero, ¿qué pasa cuando tenemos más de un cliente a la vez?

- Primero arreglar el tema de que varios clientes no puedan pisarse una misma butaca. (El problema creado por el espacio de tiempo entre que se consulta la lista de butacas libres y se completa el proceso de ocupar una de ellas.)

- Luego explorar el tema de lo que puede tardar cada cliente en completar el proceso cuando hay unos pocos clientes vs. cuando hay un pico de un centenar de clientes o así.

  ¿Cómo se podría hacer que el proceso fuera más rápido (más escalable) en el caso del pico con centenares de clientes?

#### B

Tener la ubicación de cada butaca en todas las copias del patio de butacas es un malgasto de memoria, ya que las butacas están fijas. Convendría tener algo así como una "copia maestra" del patio de butacas a donde consultar la ubicación de una butaca usando su id

- Modelar dicha consulta con un retardo aleatorio de respuesta (latencia) y con la posibilidad de que no responda (error de comunicaciones). Simulando un servicio corriendo en otra máquina distinta.

- Explorar cómo se comporta el sistema cuando hay pocos clientes (pocas consultas) vs. muchos clientes y muchas butacas libres (muchas consultas).



## Tareas extras (opcionales)

Para la última pregunta de A. Explorar vías alternativas de realizar el proceso. No tiene por qué ser igual al que era cuando se compraba en taquillas físicas: cada cliente, en el turno en que le toque (cuando llega a la taquilla), elige tranquilamente asientos y los paga.

- ¿Podría ser algo más abreviado: 

  - Solicitar x número de asientos y pagarlos.

  - El sistema asigna automáticamente los mejores asientos de entre los que están libres en ese momento?.

    (Así se evitan los problemas de estar reservando asientos mientras la persona se decide.)


- ¿Podría ser con distintas fases: 

  - Los clientes deciden tranquilamente qué asientos desean y hacen una puja por ellos. Pudiendo hacer distintas pujas por distintos asientos. (Una subasta)
 
  - Unos días más tarde, el sistema resuelve las pujas y asigna cada asiento al mejor postor.

  - Hasta el día del evento, se abre un periodo de intercambios entre clientes: a quien le sobren asientos, los pone a la venta; quien no ha obtenido asiento, hace propuestas a quienes los tengan. (Un mercado peer-to-peer)

  (Así se evitan los problemas de un pico enorme de solicitudes en la primera hora de apertura de taquillas.)
