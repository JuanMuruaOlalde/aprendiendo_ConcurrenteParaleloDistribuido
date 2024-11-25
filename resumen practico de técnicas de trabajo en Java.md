# Resumen de técnicas de trabajo concurrente en Java

## Trabajando con procesos

Un proceso es un programa secundario independiente, con su propio espacio de memoria. Los procesos no comparten nada entre ellos, ni los procesos secundarios entre sí, ni estos con el proceso principal. (Salvo comunicación a través del sistema operativo.)

Los procesos se utilizan cuando se quiere utilizar otros comandos o programas ya existentes. (Incluso sin acceso a su código fuente.)

nota: ver en la carpeta \ariketa\practicas, los ejercicios 1.01 y 1.07

### Crear un nuevo proceso

Se utiliza un ProcessBuilder. Con el se define el comando a lanzar para arrancar el programa y también los parámetros a pasarle en ese lanzamiento.
````
ProcessBuilder pb;
pb = new ProcessBuilder("java.exe", "Sumador","76","2");
````

Se crea un nuevo proceso cada vez que se llama al método .start() del ProcessBuilder.
````
Process p = pb.start();

````


nota: ProcessBuilder admite indicarle la carpeta a donde ir a buscar el programa:
````
pb.directory();

nota: `java.nio.file.Paths.get(LanzadorDeProcesosMain.class.getResource(".").toURI()).toFile()` 
obtiene la carpeta donde está la .class del programa principal que se está ejecutando.
```` 

nota: ProcessBuilder admite también indicar valores específicos para ciertas variables de entorno:
````
Map<String, String> entorno = pb.environment();
entorno.put("nombredelavariable", "valor");
````
(Esta es otra manera de pasarle parámetros a un programa.)


### Comunicación entre procesos

Tal y como se ha indicado, cada proceso es independiente. La única forma de comunicación entre ellos es externa. Por ejemplo:

- Los canales de entrada (in) o salida (out) del sistema operativo.

- Canales a través de la red local; por ejemplo, usando sockets.

- Usando almacenamiento en disco, a través de un archivo compartido.

- etc, etc. [IPC - Inter-Process Communication](https://en.wikipedia.org/wiki/Inter-process_communication)

Lo único que viene "de serie" y es lo que se utiliza habitualemnte. Es la posibilidad de que el proceso principal controle los canales de entrada (in) o salida (out) de los procesos secundarios.

Para enviarle algo a un proceso secundario. Algo que el proceso secundario recoge como si le viniera desde el teclado (System.in) o desde los argumentos iniciales (args)
````
try (OutputStreamWriter paraEnviar = new OutputStreamWriter(proceso.getOutputStream(), "UTF-8");){
    paraEnviar.write(algo);
    paraEnviar.flush();
}
````

Para recibir todo lo que un proceso secundario envia a la pantalla (System.out)
````
try (BufferedReader paraRecoger = new BufferedReader(new InputStreamReader(proceso.getInputStream()));){
    String linea;
    while ( (linea = paraRecoger.readLine()) != null) {
        System.out.println("  secundario(stdout): " + linea );
    }
}
````

nota: Los errores también se envian a la pantalla, pero por un canal diferente (System.err)
````
try (BufferedReader paraRecogerErrores = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));){
    String linea;
    while ( (linea = paraRecogerErrores.readLine()) != null) {
        System.out.println("  secundario(stderr): " + linea );
    }
}
````

De todas formas, esta manera de trabajar, interviendo en los canales de entrada y de salida, permite solo una interacción simple entre un proceso principal y sus secundarios. No está pensado para diálogos complejos.

Es más bien algo parecido al clásico encadenamiento de comandos: (https://www.youtube.com/watch?v=5-wnAO5G7n0&list=PLSmXPSsgkZLuJKJhvL1U384aHesbVDekO&index=13)


## Trabajando con hebras

Trabajando con hebras hay un solo programa, el programa principal (la hebra principal).

Una hebra secundaria es una porción del código del programa que se ejecuta de forma independiente. 

Todas las hebras, tanto las secundarias como la principal, comparten todo entre ellas; accediendo a un mismo espacio de memoria. 

(De ahí que nos tengamos de preocupar nosotros que cada hebra sea lo más independiente posible de las demás y de que en ningún momento se interfieran las unas a las otras.)

Las hebras se utilizan cuando se quiere aprovechar tiempos de procesamiento que de otra manera quedarian desaprovechados. O cuando tenemos varios procesadores que pueden trabajar a la vez.

nota: ver en la carpeta \ariketa\practicas, los ejercicios 2.02 , ...

### Crear una nueva hebra

Se puede definir una hebra (thread) a partir de cualquier clase que implemente `Runnable` y, por tanto, tenga un método `run()`
````
public class Secundaria implements Runnable {

    @Override
    public void run() {
        -----
        ----
        ---
        ----
    }
}
````

````
Thread hebraSecundaria = new Thread(new Secundaria());
````

Se crea un nuevo hilo de ejecución cada vez que se llama al método .start() de la definición de la hebra.
````
hebraSecundaria.start();
````

Cada uno de los hilos de ejecución que se arranquen está ejecutando el código de run() de forma paralela. Sin esperarse unos a otros, ni tener en cuenta lo que hacen los demás. Todo el proceso de comunicación y coordinación ha de estar expresamente codificado.


### Comunicación y coordinación entre hebras

#### Usando variables compartidas

En casos sencillos, se pueden definir variables globales (que todas las hebras pueden acceder). Como por ejemplo:
````
    static volatile String peticionCompartida = "";
    static volatile Boolean hayUnaNuevaPeticionEnCurso = false;
    static volatile Integer resultadoCompartido;

````

Mejor si esas variables compartidas son de alguno de los tipos de [java.util.concurrent.atomic](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/concurrent/atomic/package-summary.html)
````
    static AtomicBoolean hayUnaNuevaPeticionEnCurso = new AtomicBoolean(false);
    static AtomicInteger resultadoCompartido = new AtomicInteger();
````
Estos tipos atómicos obligan a utilizar sus propios métodos seguros ("thread-safe") de lectura y escritura. Por ejemplo:
````
hayUnaNuevaPeticionEnCurso.set(true);
hayUnaNuevaPeticionEnCurso.getAcquire();

resultadoCompartido.addAndGet(1);
resultadoCompartido.getAndIncrement();
````
(https://www.baeldung.com/java-volatile-vs-atomic)


nota: En casos muy simples, suele ser ser más o menos seguro usar variables normales `volatile` tal cual. Pero es posible que haya casos en los que sea necesario regular manualmente el acceso a esas variables compartidas para garantizar su integridad. (Ver más adelante, el apartado de "Regulando manualmente el acceso a recursos compartidos".) 

#### Usando estructuras de datos pensadas para ser compartidas

En casos donde se intercambian grandes cantidades de información, son muy convenientes las colecciones de datos especializadas de la biblioteca `java.util.concurrent`.

Estas coleciones especializadas son similares a las colecciones habituales, solo que están preparadas para ser usadas con seguridad en entornos compartidos. Están expresamente diseñadas para que sean "thread-safe".

(https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/package-summary.html#queues-heading)

(https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/package-summary.html#concurrent-collections-heading)

Podemos utilizar, por ejemplo:

- La cola [SynchronousQueue](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/SynchronousQueue.html). Es útil cuando se necesita escribir algo en una hebra para luego leer ese algo desde otra hebra. 

- Alguna de las [colas de espera](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/BlockingQueue.html), tales como [ArrayBlockingQueue](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/ArrayBlockingQueue.html) o [PriorityBlockingQueue](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/PriorityBlockingQueue.html). Son útiles cuando se necesita escribir secuencialmente varias cosas desde cualquier hebra y luego leerlas en secuencia FIFO (primera en entrar, primera en salir) desde cualquier otra hebra.

  nota: Estas colas de espera pueden construirse con un tamaño de cola limitado. Y, en ese caso, pueden funcionar de forma parecida a semáforos a la hora de limitar el número de hebras que pueden acceder simultáneamente al recurso.

- La lista [CopyOnWriteArrayList](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/CopyOnWriteArrayList.html). Es útil cuando se tienen pocas escrituras, pero muchísimas lecturas concurrentes.

- Alguno de los diccionarios concurrentes, tales como [ConcurrentHashMap](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html) o [ConcurrentSkipListMap](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/ConcurrentSkipListMap.html). Son útiles cuando se necesita un diccionario "thread-safe" sobre el que puedan trabajar varias hebras de forma coordinada.


#### Usando "tuberias" (Pipes)

`java.io.PipedInputStream` y `java.io.PipedOutputStream` permiten a dos hebras comunicarse. El flujo de datos es unidireccional:
- por un lado de la tuberia (PipedOutputStream) se envian datos
- y por el otro (PipedInputStream) se reciben.
````
java.io.PipedOutputStream paraEnviar = new java.io.PipedOutputStream();
java.io.PipedInputStream paraRecibir = new java.io.PipedInputStream(paraEnviar);)
````
Un lado de la tuberia:
````
paraEnviar.write(---datos---);
paraEnviar.flush();
````
El otro lado de la tuberia:
````
---datos--- = paraRecibir.read()
````

No es recomendable mezclar ambos lados. Si se necesita comunicación bidireccional, es necesario montar otra tuberia diferente para ese otro sentido de comunicación.

aviso: Obviamente, un lado no puede recibir hasta que el otro envia. Cuando la hebra receptora llama a `.read()`, su ejecución se bloquea hasta que recibe algo (o hasta que la tuberia se cierra). Por lo demás, la coordinación en la comunicación corre por nuestra cuenta...

nota: Podemos usar el método `.available()` del lado receptor para consultar, sin bloquear, a ver si hay algo pendiente de recibir o no. 



#### Regulando manualmente el flujo de ejecución

Hay momentos en que una hebra no puede continuar su ejecución hasta que otra termine de hacer algo o hasta que se cumpla cierta condición.

¡importante! Cuando se comienza a regular el flujo de ejecución, es vital asegurarse de que no se producen situaciones indeseadas tales como:

- Una hebra se queda esperando para siempre. (Pérdida de respuesta)

- Una hebra espera durante tiempos en que podria aprovecharlos para estar haciendo alguna otra cosa. (Pérdida de rendimiento)

##### con puntos de encuentro (join)

Una hebra puede pararse a esperar a otra en cualquier punto del código.
```
otraHebra.join()
```
La hebra que ha ejecutado el join() queda suspendida en ese punto hasta que la otra hebra termina todo su trabajo y desaparece.

##### con monitores (synchronized)

Una hebra puede pararse en cualquier punto del código a esperar la señal de un monitor concreto:
```
synchronized(objetoMonitor) {
    wait()
}
```

Y no despierta hasta que alguna otra hebra llama a:
```
synchronized(objetoMonitor) {
    notify()
}
```
* notify() despierta a una de las hebras que estuvieran esperando a ese monitor.
* notifyAll() despierta a todas las hebras que estuvieran esperando a ese monitor.

nota: Para evitar que una hebra se quede esperando para siempre, porque no reciba nunca una señal nofify(). Se puede realizar la llamada a wait() indicando un tiempo máximo de espera: wait(n)

nota: Suele ser también bastante habitual que la parte de espera esté guardada con alguna condición. Para asegurar que la hebra, aunque se despierte, no siga adelante si no se ha cumplido esa condición.
````
while (! secumplelacondicion) {
    synchronized(objetoMonitor) {
        wait()
    }
}
aquivaeltrabajoahacer

../..
````


#### Regulando manualmente el acceso a recursos compartidos

Cuando usamos recursos "normales"; es decir, recursos que no son expresamente diseñados para ser "thread-safe". La responsabilidad de implementar la seguridad en las lecturas y escrituras recae directamente sobre nosotros. 

Esto se consigue bloqueando el recurso compartido mientras un hilo de ejecución lo esté usando. Para evitar que otro no se entrometa en medio cuando no debe.

Y se hace con algún tipo de herramienta mutex (MUTual EXclusion).

##### con monitores (synchronized)

La parte de código a proteger se marca con la palabra clave `synchronized`

Por ejemplo,
````
public class ClaseConRecursosProtegidos {

    class ContadorSofisticado {
        private String claveDeComprobación = "esvalida";
        private volatile int cuenta = 0;

        public void incrementarCuenta() {
            // nota: pensar como si este incremento fuese costoso y tardase mucho tiempo
            if (claveDeComprobación.equals("esvalida")) {
                cuenta++;
            }
        }

        public int getCuantoLlevaContado() {
            return cuenta;
        }
    }

    ContadorSofisticado unContador;
    ContadorSofisticado otroContador;

    public synchronized void incrementarUnContador() {
        unContador.incrementarCuenta();;
    }

    public synchronized void incrementarElOtroContador() {
        otroContador.incrementarCuenta();
    }

    public int getCuantoLlevaContadoElUno() {
        return unContador.getCuantoLlevaContado();
    }

    public int getCuantoLlevaContadoElOtro() {
        return otroContador.getCuantoLlevaContado();
    }

}
````

Este tipo de monitores son automáticos:

- Cuando un hilo de ejecución va a comenzar a ejecutar una parte del código protegida por un monitor, tiene que esperar a que ese monitor le de acceso (es decir, a que no haya ningún otro hilo ejecutando esa parte del código).

- Mientras un hilo está ejecutando esa parte del código, ese monitor está bloqueado y no permite a ningún otro hilo ejecutar esa parte.

- Cuando un hilo termina con la última instrucción en esa parte del código, ese monitor se desbloquea.


##### con semáforos

Un semáforo es un tipo de monitor que permite a más de un hilo de ejecución el acceso simultáneo al recurso compartido que protege.

````
aquí falta poner algún ejemplo...
````

Tiene un número fijo de concesiones.
- Se concede una cuando se llama a la función .acquire()
- Se libera una cuando se llama a la función .release()

La hebra que llama a .acquire() queda "dormida" hasta que el semáforo tiene una concesión libre.

La hebra que tiene una concesión, ha de liberarla cuando termina de usar el recurso compartido. Si no lo hace, esa concesión quedará bloqueada para siempre.


##### con barreras

Una barrera es un tipo de monitor que permite sincronizar la ejecución de varias hebras. La barrera protege un grupo de hebras; a medida que cada hebra adquiere la barrera, esa hebra queda a la espera; hasta que todas ellas han adquirido la barrera y todas se liberan para seguir ejecutandose.

````
aquí falta poner algún ejemplo...
````

Se usan cuando varios hilos han de completar previamente un subtrabajo determinado para luego poner en común esos resultados parciales antes de seguir trabajando.

##### con algún otro tipo de mutex especializado

Para otros tipos de casos específicos, a veces en la biblioteca suele haber algo optimizado expresamente para ello. Pero su uso es muy especializado y no suele ser habitual.

(https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/concurrent/package-summary.html#synchronizers-heading)


