import java.util.ArrayList;

public class Main {

    static final int CUANTAS_HEBRAS_CONCURRENTES = 15;

    public static void main(String[] args) throws InterruptedException {
        Banco banco = new Banco();
        banco.InicializarParaPruebas();
        System.out.println("Total de activos iniciales: " 
            + String.format("%,.2f", banco.getActivosTotalesEnEuros()) + " euros."
            );

        java.util.ArrayList<Thread> listaDeHilos = new ArrayList<>();
        // arrancar varios hilos de ejecución...
        for (int i = 0; i < CUANTAS_HEBRAS_CONCURRENTES; i++){
            Thread hilo = new Thread(new EjecutarTrasferenciasDePruebas(banco));
            hilo.start();
            listaDeHilos.add(hilo);
        }
        // hacer que el hilo principal (este hilo, main) espere a los demás...
        for (Thread hilo : listaDeHilos){
            hilo.join();
        }

        System.out.println("Total de activos finales: " 
            + String.format("%,.2f", banco.getActivosTotalesEnEuros()) + " euros."
            );
    }


}
