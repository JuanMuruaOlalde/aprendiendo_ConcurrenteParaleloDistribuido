# Cine

## Introducción

Operaciones que son simples y claras en una ejecución secuencial, pueden llegar a ser problemáticas en una ejecución concurrente/paralela/distribuida.


## Tareas a realizar

Modelar un `Cine` con un patio de `Butaca`s

Habrá varias copias de dicho patio de butacas, una por cada sesión.

Cada `Butaca` tiene:

- Un identificador único `id`, un texto indicando "fila::numero".

- Una `ubicación`, indicando su distancia 2D desde un origen en la parte inferior-central de la pantalla. La distancia x será los metros cerca/lejos que esté y la distancia y será los metros derecha(+)/izquierda(-) que esté (visto por una persona sentada en la butaca, mirando a la pantalla).

- Un booleano `estaLibre`.

Pensando en que estamos en una sesión concreta (una copia concreta del patio de butacas). Modelar el proceso para permitir a una hebra `Cliente`: 

- Recibir una lista de las butacas libres.

- Seleccionar una butaca (la hebra escogerá una aleatoriamente).

- Pagar (llamar a una función que tarde un cierto tiempo aleatorio en responder)

- Marcar esa butaca como ocupada.

### Puntos a comprobar

#### A

Con un solo cliente, la cosa es sencilla. Pero, ¿qué pasa cuando tenemos más de un cliente a la vez?

- Primero arreglar el tema de que varios clientes no puedan pisarse una misma butaca. (El problema creado por el espacio de tiempo entre que se consulta la lista de butacas libres y se completa el proceso de ocupar una de ellas.)

- Luego explorar el tema de lo que puede tardar cada cliente en completar el proceso cuando hay unos pocos clientes vs. cuando hay un pico de un centenar de clientes o así.

  ¿Cómo se podria hacer que el proceso fuera más rápido (más escalable) en el caso del pico con centenares de clientes?

#### B

Tener la ubicación de cada butaca en todas las copias del patio de butacas es un malgasto de memoria, ya que las butacas están fijas. Convendria tener algo así como una "copia maestra" del patio de butacas a donde consultar la ubicación de una butaca usando su id

- Modelar dicha consulta con un retardo aleatorio de respuesta (latencia) y con la posibilidad de que no responda (error de comunicaciones). Simulando un servicio corriendo en otra máquina distinta.

- Explorar cómo se comporta el sistema cuando hay pocos clientes (pocas consultas) vs. muchos clientes y muchas butacas libres (muchas consultas).


### Posibilidad de simplificar la práctica



## Tareas extras (opcionales)

