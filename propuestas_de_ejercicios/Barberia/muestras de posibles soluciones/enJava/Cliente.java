public class Cliente implements Runnable {

    @Override
    public void run() {
        
        String miNombre = Thread.currentThread().getName();

        try {
            System.out.println("  " + miNombre + ": Esperando a entrar en la barberia.");
            Barberia.salaDeEspera.acquire();
            System.out.println("  " + miNombre + ": He entrado en la barberia. Estoy en la sala de espera.");

            //Barberia.barbero.acquire();
            Barberia.atenderCliente(miNombre); // .atenderCliente es "synchronized"; es decir, tiene su propio bloqueo. 
            // Por tanto, para un solo barbero no es necesario el semáforo.

            //Barberia.barbero.release();
            System.out.println("  " + miNombre + ": He terminado. Salgo de la barberia.");

        } catch (InterruptedException e) {
            System.out.println("  " + miNombre + ": He MUERTO. Han interrumpido mi hilo de vida.");
        }
    }
    
}
