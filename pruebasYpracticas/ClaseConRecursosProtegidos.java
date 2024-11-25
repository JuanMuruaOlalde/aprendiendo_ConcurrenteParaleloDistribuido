public class ClaseConRecursosProtegidos {

    class ContadorSofisticado {
        private String claveDeComprobación = "esvalida";
        private volatile int cuenta = 0;

        public void incrementarCuenta() {
            // nota: pensar como si este incremento fuese costoso y tardase mucho tiempo
            if (claveDeComprobación.equals("esvalida")) {
                cuenta++;
            }
        }

        public int getCuantoLlevaContado() {
            return cuenta;
        }
    }

    ContadorSofisticado unContador;
    ContadorSofisticado otroContador;

    public void incrementarUnContador() {
        synchronized (unContador) {
            unContador.incrementarCuenta();;
        }
    }

    public void incrementarElOtroContador() {
        synchronized (otroContador) {
            otroContador.incrementarCuenta();;
        }
    }

    public int getCuantoLlevaContadoElUno() {
        return unContador.getCuantoLlevaContado();
    }

    public int getCuantoLlevaContadoElOtro() {
        return otroContador.getCuantoLlevaContado();
    }

}
