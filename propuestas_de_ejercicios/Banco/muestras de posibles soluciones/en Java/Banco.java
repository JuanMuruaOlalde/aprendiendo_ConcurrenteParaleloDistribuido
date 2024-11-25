public class Banco {

    public static final int NUMERO_DE_CUENTAS_PARA_PRUEBAS = 100;
    public static final Dinero SALDO_INICIAL_PARA_PRUEBAS = new Dinero(100000); // 100000 eurocents = 1000 euros
    
    java.util.HashMap<java.util.UUID, Cuenta> listaDeCuentasActivas;

    public Banco() {
        listaDeCuentasActivas = new java.util.HashMap<>();
    }
    
    public void InicializarParaPruebas() {
        listaDeCuentasActivas = new java.util.HashMap<>();
        for (int i = 0; i < NUMERO_DE_CUENTAS_PARA_PRUEBAS; i++) {
            listaDeCuentasActivas.put(java.util.UUID.randomUUID(), new Cuenta(SALDO_INICIAL_PARA_PRUEBAS));
        }
    }

    public Dinero getActivosTotales() {
        int contador = 0;
        for (Cuenta cuenta : listaDeCuentasActivas.values()) {
            if (cuenta.getSaldo().getMoneda().equals(Dinero.MONEDA_POR_DEFECTO)) {
                contador = contador + cuenta.getSaldo().getCantidad();
            } else {
                // TODO , por ahora no trabajamos con mas monedas.
                throw new UnsupportedOperationException("Todavia no trabajamos con varias monedas diferentes.");
            }
        }
        return new Dinero(contador);
    }

    public Double getActivosTotalesEnEuros() {
        return getActivosTotales().getCantidad() / 100.0;
    }

// TODO , este es el sitio para probar con y sin protección frente a multiejecución (synchronized)
    // public synchronized String  TransferirEntreCuentasInternas(Cuenta origen, Cuenta destino, Dinero cantidad) {
    public String  TransferirEntreCuentasInternas(Cuenta origen, Cuenta destino, Dinero cantidad) {
            StringBuilder resultado = new StringBuilder();
        resultado.append(origen.SacarDinero(cantidad));
        if (!resultado.toString().contains("[Error]")) {
            resultado.append(" ==> ");
            resultado.append(destino.IngresarDinero(cantidad));
        }
        return resultado.toString();
    }

    public java.util.Optional<Cuenta> getUnaCuentaCualquieraAlTuntun() {
        int indiceAleatorio = (int) ((Math.random() * Banco.NUMERO_DE_CUENTAS_PARA_PRUEBAS) + 1);
        int i = 1;
        for (Cuenta cuenta : listaDeCuentasActivas.values()) {
            if (i == indiceAleatorio) {
                return java.util.Optional.of(cuenta);
            }
            i++;
        }
        return java.util.Optional.empty();
    }


    // TODO , Aqui faltaria poner toda la infraestructura real para añadir cuentas, buscar cuentas, suprimir cuentas, etc, etc...
    //        además del resto de la operatoria real con clientes.

}
