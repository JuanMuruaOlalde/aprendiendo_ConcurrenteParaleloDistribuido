# Descuentos

## Introducción

Una serie de comprobaciones secuenciales es muy sencilla de realizar si todo el sistema es secuencial y corre en una sola hebra.

Pero en sistemas concurrentes (como el descrito en las tareas a realizar)...

¿Qué pasa si, por ejemplo, dos pedidos casi simultáneos de un mismo cliente van procesándose en sendas hebras, sin haber saldo suficiente para ambos pero sí para cada uno por separado. Y resulta que ambos pasan en el mismo momento la comprobación de saldo (antes de terminar de procesarse ninguno de los dos).?

¿Qué pasa si, por ejemplo, un cliente sabe que ya ha comprado por valor de 347 y quiere comprar dos artículos más (uno de 4 y otro de 19). Lanza dos pedidos seguidos, uno detrás de otro, primero el de 4 y luego el de 19 (con la idea se superar 350 con el primero y obtener mayor beneficio del descuento en el segundo). Pero resulta que la hebra del segundo pedido va más rápida que la del primero y se procesa antes.?



## Tareas a realizar

Un cliente tiene la posibilidad de hacer pedidos rápidamente.

Si es un Cliente Distinguido y ya ha comprado por valor de más de 350 en las últimas 2 semanas, tiene un descuento del 20%; si en las dos últimas semanas ha comprado por debajo de 350, tiene un descuento del 10%.

Si no es un Cliente Distinguido y ya ha comprado por valor de más de 350 en la última semana, tiene un descuento del 10%; si en la última semana ha comprado por debajo de 350, no tiene descuentos.

Para que los pedidos se puedan procesar con rapidez, la compra va contra un una cartera prepago que el cliente ha rellenado previamente con un determinado saldo.

Si en el momento de la compra hay saldo suficiente, se realiza la compra. Pero si no, se notifica un error y es como si el cliente no hubiera hecho nada.

Modelar un sistema en el que cada compra es procesada por una hebra (se lanza una hebra por cada pedido que llega). Asegurando en todo momento la integridad del crédito de cada cliente, del histórico de compras realizadas y de los descuentos aplicados.


