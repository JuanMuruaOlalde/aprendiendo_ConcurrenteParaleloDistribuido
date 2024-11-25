class Cuenta {

    private java.util.UUID id;
    private Dinero saldo;

    public Cuenta() {
        saldo = new Dinero(0);
    }

    public Cuenta(Dinero saldoInicial) {
        saldo = saldoInicial;
    }

    public Dinero getSaldo() {
        return saldo;
    }

    public String SacarDinero(Dinero cuanto) {
        if (cuanto.getMoneda().equals(saldo.getMoneda())) {
            if (cuanto.getCantidad() <= saldo.getCantidad()) {
                saldo.setCantidad(saldo.getCantidad() - cuanto.getCantidad());
                return "[Bien], se ha sacado " 
                    + String.format("%,d", cuanto.getCantidad()) 
                    + " y queda de saldo " + String.format("%,d", saldo.getCantidad());
            } else {
                return "[Error], saldo insuficiente, se ha intentado sacar " 
                    + String.format("%,d", cuanto.getCantidad()) 
                    + " y solo habia " + String.format("%,d", saldo.getCantidad());
            }
        } else {
            // TODO , por ahora no trabajamos con varias monedas.
            return "[Error], no trabajamos con " + cuanto.getMoneda();
        }
    }

    public String IngresarDinero(Dinero cuanto) {
        if (cuanto.getMoneda().equals(saldo.getMoneda())) {
            saldo.setCantidad(saldo.getCantidad() + cuanto.getCantidad());
            return "[Bien], se ha ingresado " 
                + String.format("%,d", cuanto.getCantidad()) 
                + "; con lo que el saldo es ahora de " + String.format("%,d", saldo.getCantidad());
        } else {
            // todavia no trabajamos con varias monedas.
            return "[Error], no trabajamos con " + cuanto.getMoneda();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cuenta other = (Cuenta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}