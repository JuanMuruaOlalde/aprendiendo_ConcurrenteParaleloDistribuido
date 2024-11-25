import java.util.concurrent.atomic.AtomicBoolean;

public class Cliente_varios_barberos_ implements Runnable {

    private String miNombre;
    private AtomicBoolean heTerminado = new AtomicBoolean(false);

    private Cliente_varios_barberos_() {
        // es private para que nadie pueda crear clientes sin nombre
    }
    public Cliente_varios_barberos_(String nombre) {
        miNombre = nombre;
    }

    @Override
    public void run() {
        long inicio = System.nanoTime();
        
        try {
            System.out.println("  " + miNombre + ": Esperando a entrar en la barberia.");
            Barberia_varios_barberos_.salaDeEspera.put(this);
            System.out.println("  " + miNombre + ": He entrado en la barberia. Estoy en la sala de espera.");

            while(!heTerminado.get()){
                synchronized(this) {
                    wait();
                }
            }

            long fin = System.nanoTime();
            System.out.println("  " + miNombre + ": He terminado. Salgo de la barberia. (tiempo = " + (fin - inicio) + ")");
        } catch (InterruptedException e) {
            System.out.println("  " + miNombre + ": He MUERTO. Han interrumpido mi hilo de vida.");
        }
    }

    public void terminar() {
        heTerminado.set(true);
        synchronized(this) {
            notify();
        }
    }

    public String getNombre() {
        return miNombre;
    } 
    
}
