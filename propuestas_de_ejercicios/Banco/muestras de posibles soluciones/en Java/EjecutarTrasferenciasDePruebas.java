public class EjecutarTrasferenciasDePruebas implements Runnable {

    static final int CUANTAS_TRANSFERENCIAS_DE_PRUEBA = 35;
    static final int MAX_EUROCENT_EN_CADA_TRANSFERENCIA = 10000;

    Banco banco;
    
    public EjecutarTrasferenciasDePruebas(Banco banco) {
        this.banco = banco;
    }

    @Override
    public void run() {

        for (int i = 0; i < CUANTAS_TRANSFERENCIAS_DE_PRUEBA ; i++) {

            java.util.Optional<Cuenta> cuentaOrigen = banco.getUnaCuentaCualquieraAlTuntun();
            java.util.Optional<Cuenta> cuentaDestino = banco.getUnaCuentaCualquieraAlTuntun();
            if (cuentaOrigen.isPresent() && cuentaDestino.isPresent()) {

                Dinero cantidad = new Dinero((int) ((Math.random() * MAX_EUROCENT_EN_CADA_TRANSFERENCIA) + 1));

                String resultado = banco.TransferirEntreCuentasInternas(cuentaOrigen.get(), cuentaDestino.get(), cantidad);
                System.out.println(resultado);
            }

        }

    }
    
}
