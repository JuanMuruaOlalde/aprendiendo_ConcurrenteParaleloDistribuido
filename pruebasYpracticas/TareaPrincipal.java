import java.util.ArrayList;

class TareaPrincipal {

    private static ArrayList<String> almacenDeClaves = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("TareaPrincipal: comienzo.");
        long comienzo = System.nanoTime();

        ArrayList<Thread> listaDeHilosLanzados = new ArrayList<>();
        for (int i = 1; i <= 5 ; i++) {
            Thread hilo = new Thread(new CalcularClavesSecretas());
            hilo.setName("hiloCalculador_" + i + "_");
            hilo.start();
            listaDeHilosLanzados.add(hilo);
        }
        for (Thread hilo : listaDeHilosLanzados) {
            hilo.join();
        }

        System.out.println("TareaPrincipal: en el almacen hay guardadas " + almacenDeClaves.size() + " claves.");
        for (String clave : almacenDeClaves) {
            System.out.println(clave);
        }

        System.out.println("TareaPrincipal: fin.");
        long fin = System.nanoTime();
        System.out.println("Se ha tardado " 
            + (fin - comienzo) / 1000000 + " milisegundos.");
    }

    public static synchronized void  GuardarClave(String clave) {
            // se supone que guardar una clave tarda mucho...
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        almacenDeClaves.add(clave);
    }

    public static synchronized ArrayList<String> getListaDeClavesGuardadas() {
        ArrayList<String> copiaDelAlmacen = new ArrayList<>();
        for (String clave : almacenDeClaves) {
            copiaDelAlmacen.add(clave);
        }
        return copiaDelAlmacen;
    }

}