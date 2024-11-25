# Trabajando con funciones asíncronas

## Ejecución asíncrona vs. ejecución síncrona

Función asíncrona es aquella en que:

- Tras llamarla no se espera a que devuelva el resultado. 

- Solo se "toma nota" de algún modo de lo que hacer cuando devuelva el resultado (normalmente apuntando de algún modo hacia una función auxiliar "callback" o suministrando directamente dicha función con una expresión lambda). 

- La ejecución del código principal continua haciendo otras cosas.

- En algún momento, en el futuro, cuando el resultado esté disponible (o no), se ejecutará la función auxiliar "callback" o lambda.

Es decir, las "otras cosas" que se hagan en el tiempo que tarda en completarse la tarea asíncrona no pueden contar con tener disponible el resultado de dicha llamada. No pueden hacer nada que necesite tener dicho resultado.

Esto es completamente distinto a un funcionamiento síncrono. Donde todas y cada una de las líneas de código se ejecutan y completan antes de pasar a la siguiente. (Al llamar a una función, se espera a que esta termine y devuelva su resultado.) Es decir, en cualquier punto del código siempre se puede contar con tener disponibles los resultados de todas las líneas de código por donde se ha pasado anteriormente.

El funcionamiento asíncrono es bastante común en arquitecturas distribuidas y basadas en servicios. En estas arquitecturas se usa para: 
- Desacoplar entre sí las distintas partes. (Una parte pide a otra que haga algo -o notifica a otra que ha sucedido algo-... y se olvida de ello... para poder seguir trabajando). 
- Evitar incertidumbres/bloqueos al interactuar con partes que pueden tener alta latencia o pueden estar caidas. (Una parte pide a otra que haga algo y le devuelva una respuesta... que recibirá en otra función auxiliar que queda a la espera... pero el código principal sigue trabajando en otra cosa).

(https://www.codeproject.com/Articles/5299501/Async-Await-Explained-with-Diagrams-and-Examples)

[Understanding Retry Architectures](https://techholding.co/blog/understanding-message-queues-and-retry-architectures/)

[Spring microservices resilience with Retry and Fallback](https://medium.com/@AlexanderObregon/spring-microservices-resilience-with-retry-and-fallback-mechanisms-8500208fc463)

## Ejecución paralela

Es importante no confundir asincronicidad con paralelismo.

Una ejecución paralela puede ser perfectamente síncrona. Por ejemplo si cada hebra paralela ejecuta todas sus líneas de código secuencialmente, una detrás de otra. Es decir, cada hebra es totalmente independiente una de otra; cada una hace su trabajo. Y todas ellas van aportando al resultado final según terminan su ejecución.

El comportamiento asíncrono en ejecuciones paralelas aparece solo en aquellos casos en que una hebra necesita utilizar o compartir algo de otra hebra. La hebra receptora, cuando llega al punto donde necesita de la otra, como no sabe cuándo se lo dará esa otra, ha de:
- Esperar -bloquearse- hasta que se lo dé. 
- Delegar la espera en una función auxiliar - callback, promise,...-, para poder aprovechar ese tiempo para hacer otras cosas.

Si opta por lo primero, esperar, tendriamos un comportamiento síncrono. Si opta por lo segundo, delegar, tendriamos un comportamiento asíncrono.


(https://stackoverflow.com/a/748235/11078359)


## Asynchronous tasks in javascript: callbacks, promises,...

(https://dev.to/kelvinguchu/mastering-asynchronous-javascript-promises-asyncawait-error-handling-and-more-41ph)

(https://dev.to/nziokidennis/javascript-asynchronous-programming-concepts-and-best-practices-38k7)

(https://blog.logrocket.com/understanding-asynchronous-javascript/)


### Calling asychronous functions in javascript:  async/await vs. then 

(https://stackoverflow.com/questions/34401389/what-is-the-difference-between-javascript-promises-and-async-await)

The first things you have to understand that async/await syntax is just syntactic sugar which is meant to augment promises. In fact the return value of an async function is a promise. async/await syntax gives us the possibility of writing asynchronous in a synchronous manner. Here is an example:

````
//Promise chaining:

function logFetch(url) {
  return fetch(url)
    .then(response => response.text())
    .then(text => {
      console.log(text);
    }).catch(err => {
      console.error('fetch failed', err);
    });
}


//Async function:

async function logFetch(url) {
  try {
    const response = await fetch(url);
    console.log(await response.text());
  }
  catch (err) {
    console.log('fetch failed', err);
  }
}
````

In the above example the await waits for the promise (fetch(url)) to be either resolved or rejected. If the promise is resolved the value is stored in the response variable, and if the promise is rejected it would throw an error and thus enter the catch block.

We can already see that using async/await might be more readable than promise chaining. This is especially true when the amount of promises which we are using increases. Both Promise chaining and async/await solve the problem of callback hell and which method you choose is matter of personal preference.


(https://javascript.info/async-await)

(https://www.smashingmagazine.com/2020/11/comparison-async-await-versus-then-catch/)


## Asynchronous tasks in Java: Callbacks, Futures, CompletableFutures,...

(https://medium.com/aruva-io-tech/a-comprehensive-introduction-to-asynchronous-programming-in-java-promises-callbacks-and-futures-747ddfaf3c4b)

## Asynchronous tasks in .NET: TAP

TAP (Task Asynchronous Programming model)

(https://learn.microsoft.com/en-us/dotnet/csharp/asynchronous-programming/)

[What happens in an async method](https://learn.microsoft.com/en-us/dotnet/csharp/asynchronous-programming/task-asynchronous-programming-model#BKMK_WhatHappensUnderstandinganAsyncMethod)

## Fearless Concurrency in Rust

(https://doc.rust-lang.org/book/ch16-00-concurrency.html)

[Final Project: Building a Multithreaded Web Server](https://doc.rust-lang.org/book/ch20-02-multithreaded.html)