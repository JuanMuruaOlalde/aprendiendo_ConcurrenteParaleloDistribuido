# Banco

## Introducción

En un banco ficticio con 100 cuentas,
- cada cuenta parte con un saldo inicial de 1.000€
- solo se realizan transferencias internas entre cuentas.

No se puede sacar de una cuenta más dinero que el saldo que tenga. Esto es una limitación propia.

La suma total de saldos ha de mantenerse siempre en 100.000€ en cualquier momento dado. Este es un invariante de la situación descrita (solo transferencias internas).

## Tareas a realizar

Modelar el sistema descrito de banco, cuentas y transferencias.

Preparar un proceso que realice una serie de transferencias aleatorias entre las cuentas. Lanzar dicho proceso en varias hebras. Comprobar la suma total de saldos al final.

Probar primero sin ningún tipo de protecciones que garanticen la ejecución segura en entornos multiejecución.

Probar luego con las protecciones necesarias para garantizar que cada transferencia se realiza de forma atómica: sin interferencias entre el momento en que se saca dinero de una cuenta y el momento en que se ingresa en la otra cuenta.


## notas:

Este ejercicio está inspirado en esta serie de artículos de la revista Java Magazine:

[Synchronization in Java, Part 1: Race conditions, locks, and conditions](https://blogs.oracle.com/javamagazine/post/java-thread-synchronization-raceconditions-locks-conditions?source=:em:nw:mt::::RC_WWMK200429P00043C0058:NSL400235314)

[Synchronization in Java, Part 2: The synchronized keyword](https://blogs.oracle.com/javamagazine/post/java-thread-synchronization-synchronized-blocks-adhoc-locks?source=:em:nw:mt::::RC_WWMK200429P00043C0058:NSL400235314)

[Synchronization in Java, Part 3: Atomic operations and deadlocks](https://blogs.oracle.com/javamagazine/post/java-thread-synchronization-volatile-final-atomic-deadlocks)