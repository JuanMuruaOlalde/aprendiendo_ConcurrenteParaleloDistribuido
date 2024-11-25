import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;

class Barberia_varios_barberos_ {

    private final static Integer AFORO_DE_LA_SALA_DE_ESPERA = 5;
    public static volatile ArrayBlockingQueue<Cliente_varios_barberos_> salaDeEspera = new ArrayBlockingQueue<>(AFORO_DE_LA_SALA_DE_ESPERA);

    private final static Integer CANTIDAD_DE_BARBEROS = 3;
    public static volatile Semaphore barbero = new Semaphore(CANTIDAD_DE_BARBEROS);

    final static Integer MIN_TIEMPO_PROCESO = 1;
    final static Integer MAX_TIEMPO_PROCESO = 4;

    private final static Integer CANTIDAD_DE_CLIENTES_A_SIMULAR =  12;

    public static void main(String[] args) {
        long inicio = System.nanoTime();

        try {
            System.out.println("=====================================================");
            System.out.println("Barberia: iniciando simulación con varios barberos...");
            System.out.println("=====================================================");

            ArrayList<Thread> hilosDeClientes = new ArrayList<>();
            ArrayList<Thread> hilosDeBarberos = new ArrayList<>();
            ArrayList<Barbero_varios_barberos_> barberosTrabajando = new ArrayList<>();

            for (int i =  1; i <= CANTIDAD_DE_CLIENTES_A_SIMULAR; i++) {
                Cliente_varios_barberos_ cliente = new Cliente_varios_barberos_("cliente-" + i + "-");
                Thread hiloCliente = new Thread(cliente);
                hiloCliente.setName(cliente.getNombre());
                hiloCliente.start();
                hilosDeClientes.add(hiloCliente);
            }
            for (int i =  1; i <= CANTIDAD_DE_BARBEROS; i++) {
                Barbero_varios_barberos_ barbero = new Barbero_varios_barberos_("barbero-" + i + "-");
                barberosTrabajando.add(barbero);
                Thread hiloBarbero = new Thread(barbero);
                hiloBarbero.setName(barbero.getNombre());
                hiloBarbero.start();
                hilosDeBarberos.add(hiloBarbero);
            }

            for (Thread hiloCliente : hilosDeClientes) {
                hiloCliente.join();
            }

            for (Barbero_varios_barberos_ barbero : barberosTrabajando) {
                barbero.terminar();
            }
            System.out.println("Barberia: recogiendo y cerrando...");
            for (Thread hiloBarbero : hilosDeBarberos) {
                hiloBarbero.interrupt();
            }

            System.out.println("=====================================");
            System.out.println("Barberia: FIN de simulación.");
            System.out.println("=====================================");

        } catch (InterruptedException e) {
            System.out.println("Barberia: He MUERTO. Han interrumpido mi hilo de vida.");
        }

        long fin = System.nanoTime();
        System.out.println("  (tiempo = " + (fin - inicio) + ")");
    }


}