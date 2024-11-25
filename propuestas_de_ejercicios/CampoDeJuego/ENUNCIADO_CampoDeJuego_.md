# Campo de juego

## Introducción



## Tareas a realizar

Montar un `Tablero` donde se van a situar los elementos del juego.

Montar una manera de visualizar dicho tablero en la pantalla. Realizando las correspondientes conversiones entre coordenadasEnTablero y coordenadasEnPantalla

Habrá un montón de hebras, cada una manejando una `Figura`. Todas ellas accediendo a un `ControladorDeVisualizacion` para dibujarse en la pantalla. (El dibujo puede ser un simple círculo o cuadrado.)

La posición de cada figura puede ser actualizada también desde el exterior a la misma.

### Puntos a comprobar

Al dibujar una figura. Se lee la parte x de una coordenada, se convierte a pantalla, luego se lee la parte y de la coordenada, se convierte a pantalla,... ¿Qué pasa si entre ambas lecturas un hilo de ejecución externo actualiza la posición de la figura?   ¿Cómo se podria asegurar que (x,y) se trata siempre como un todo? 

### Posibilidad de simplificar la práctica

En lugar de un entorno gráfico, usar un terminal y pintar las figuras poniendo una letra X donde corresponda.



## Tareas extras (opcionales)

