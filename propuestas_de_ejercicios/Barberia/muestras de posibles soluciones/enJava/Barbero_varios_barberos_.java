import java.util.concurrent.atomic.AtomicBoolean;

public class Barbero_varios_barberos_ implements Runnable {

    String miNombre;
    private AtomicBoolean estoyTrabajando = new AtomicBoolean();

    private Barbero_varios_barberos_() {
        // es private para que nadie pueda crear barberos sin nombre
    }
    public Barbero_varios_barberos_(String nombre) {
        miNombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("El barbero " + miNombre + " comienza su jornada laboral.");
        estoyTrabajando.set(true);
        try {
            while(estoyTrabajando.get()) {
                System.out.println("El barbero " + miNombre + " está esperando al siguiente cliente...");

                // ¡aviso!: (.take() bloquea la ejecución hasta que se pueda recuperar algo de la cola)
                Cliente_varios_barberos_ cliente = Barberia_varios_barberos_.salaDeEspera.take();
                System.out.println("El barbero " + miNombre + " empieza a atender a " + cliente.getNombre() + ".");

                Double randomTiempoDeProceso = (Math.random() * Barberia_varios_barberos_.MAX_TIEMPO_PROCESO) + Barberia_varios_barberos_.MIN_TIEMPO_PROCESO;
                try {
                    Thread.sleep(randomTiempoDeProceso.intValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                cliente.terminar();
                System.out.println("El barbero " + miNombre + " termina con " + cliente.getNombre() + "." +
                                   " Ha tardado " + randomTiempoDeProceso.intValue() + " milisegundos.");
                    
            }
        } catch (InterruptedException e) {
            System.out.println("El barbero " + miNombre + " ha MUERTO. Han interrumpido su hilo de vida.");
        }

        System.out.println("El barbero " + miNombre + " ha terminado su jornada laboral.");
    }
    
    public void terminar() {
        System.out.println("El barbero " + miNombre + " comienza a recoger...");
        estoyTrabajando.set(false);
    } 

    public String getNombre() {
        return miNombre;
    } 
    
}
