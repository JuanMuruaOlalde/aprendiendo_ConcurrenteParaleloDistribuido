import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class Barberia {

    private final static Integer AFORO_DE_LA_SALA_DE_ESPERA = 5;
    public static volatile Semaphore salaDeEspera = new Semaphore(AFORO_DE_LA_SALA_DE_ESPERA);

    // Para un solo barbero, no es neceario semáforo,
    // Al ser "synchronized" el método .atenderCliente, ya tiene su propio bloqueo.
    // private final static Integer CANTIDAD_DE_BARBEROS = 1;
    // public static volatile Semaphore barbero = new Semaphore(CANTIDAD_DE_BARBEROS);

    final static Integer MIN_TIEMPO_PROCESO = 1000;
    final static Integer MAX_TIEMPO_PROCESO = 4000;
    synchronized static void atenderCliente(String nombreDelCliente) {
        salaDeEspera.release();
        System.out.println("Barberia: El barbero empieza a atender a " + nombreDelCliente + ".");
        Double randomTiempoDeProceso = (Math.random() * MAX_TIEMPO_PROCESO) + MIN_TIEMPO_PROCESO;
        try {
            Thread.sleep(randomTiempoDeProceso.intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Barberia: El barbero termina con " + nombreDelCliente + "." +
                           " Ha tardado " + randomTiempoDeProceso.intValue() + " milisegundos.");
    }

    private final static Integer CANTIDAD_DE_CLIENTES_A_SIMULAR =  12;

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("Barberia: iniciando simulación...");
        System.out.println("=====================================");

        ArrayList<Thread> hilosDeClientes = new ArrayList<>();

        for (int i =  1; i <= CANTIDAD_DE_CLIENTES_A_SIMULAR; i++) {
            Thread hiloCliente = new Thread(new Cliente());
            hiloCliente.setName("cliente-" + i + "-");
            hiloCliente.start();
            hilosDeClientes.add(hiloCliente);
        }

        for (Thread hiloCliente : hilosDeClientes) {
            try {
                hiloCliente.join();
            } catch (InterruptedException e) {
                System.out.println("Barberia: He MUERTO. Han interrumpido mi hilo de vida.");
            }
        }

        System.out.println("=====================================");
        System.out.println("Barberia: FIN de simulación.");
        System.out.println("=====================================");
    }


}