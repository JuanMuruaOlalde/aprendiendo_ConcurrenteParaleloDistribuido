public class CalcularClavesSecretas implements Runnable {

    @Override
    public void run() {
        Thread esteHilo = Thread.currentThread();

        int cuantasClaves = (int) (Math.random() * 10) + 1;
        for (int i = 1; i <= cuantasClaves ; i++) {
            // se supone que calcular una clave tarda mucho...
            try {
                Thread.sleep((int) (Math.random() * 80) + 1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("  " + esteHilo.getName() + " ==> [calculada clave" + i + "]");
            TareaPrincipal.GuardarClave("clave " + i + " del " + esteHilo.getName());
        }
        System.out.println("  " + esteHilo.getName() + " ==> [" + "he calculado un total de " + cuantasClaves + " claves" + "]");

    }
    
}
