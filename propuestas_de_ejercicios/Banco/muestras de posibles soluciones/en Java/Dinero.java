public class Dinero {

    public static final Moneda MONEDA_POR_DEFECTO = Moneda.Eurocent;

    public enum Moneda {
        Eurocent,
        Dolarcent,
        Librapenique,
        Yen,
        Renmibifen
    }

    private Integer cantidad; // se evitan los decimales para evitar problemas con los redondeos.
    private Moneda moneda;

    private Dinero(){
        // Para evitar tener una cantidad sin moneda,
        // se hace private el constructor por defecto, para que no se pueda usar.
    }
    // por defecto trabajamos con una moneda concreta.
    public Dinero(int cantidad) {
        setCantidad(cantidad);
        this.moneda = MONEDA_POR_DEFECTO;
    }
    // y este constructor queda comentado hasta que trabajemos con más monedas...
        // public Dinero(int cantidad, Moneda moneda) {
        //     setCantidad(cantidad);
        //     this.moneda = moneda;
        // }

    public void setCantidad(Integer cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        } else {
            throw new IllegalArgumentException("No se manejan cantidades negativas."
                                             + "El dinero ha de ser siempre positivo.");
        }
    }

    public Integer getCantidad() {
        return cantidad;
    }
    public Moneda getMoneda() {
        return moneda;
    }

    // esta funcion queda comentada hasta que trabajemos con más monedas...
        // public int getCantidadConvertidaALaMoneda(Moneda monedaDestino) {
        //     return cantidad * getFactorDeConversion(this.moneda, monedaDestino);
        // }
    // etc, etc,

}
