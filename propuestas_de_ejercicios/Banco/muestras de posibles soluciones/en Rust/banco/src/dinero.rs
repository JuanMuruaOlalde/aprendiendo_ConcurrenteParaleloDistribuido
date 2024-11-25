use num_format::ToFormattedString;

#[derive(Debug, PartialEq, Clone)]
pub enum Moneda {
    Eurocent,
    Dolarcent,
    Librapenique,
    Yen,
    Renmibifen,
}

impl std::fmt::Display for Moneda {
    fn fmt(&self, f: &mut std::fmt::Formatter) -> std::fmt::Result {
        match self {
            Moneda::Eurocent => write!(f, "€cent"),
            Moneda::Dolarcent => write!(f, "$cent"),
            Moneda::Librapenique => write!(f, "£penny"),
            Moneda::Yen => write!(f, "円"),
            Moneda::Renmibifen => write!(f, "圆分"),
        }
    }
}

#[derive(Debug, Clone)]
pub struct Dinero {
    cantidad: u64,
    moneda: Moneda,
}

impl Dinero {
    pub const MONEDA_POR_DEFECTO: Moneda = Moneda::Eurocent;

    pub fn new(cantidad: u64) -> Dinero {
        Dinero {
            cantidad,
            moneda: Dinero::MONEDA_POR_DEFECTO,
        }
    }

    pub fn get_cantidad(&self) -> u64 {
        self.cantidad
    }

    pub fn get_moneda(&self) -> Moneda {
        self.moneda.clone()
    }
}

impl std::fmt::Display for Dinero {
    fn fmt(&self, f: &mut std::fmt::Formatter) -> std::fmt::Result {
        write!(
            f,
            "{} {}",
            self.cantidad
                .to_formatted_string(&crate::LOCALE_POR_DEFECTO),
            self.moneda
        )
    }
}

impl std::ops::Add for Dinero {
    type Output = Dinero;

    fn add(self, other: Dinero) -> Dinero {
        if self.moneda == other.moneda {
            Dinero {
                cantidad: self.cantidad + other.cantidad,
                moneda: self.moneda.clone(),
            }
        } else {
            panic!("Todavia no se ha implementado la suma de diferentes monedas.");
        }
    }
}

impl std::ops::Sub for Dinero {
    type Output = Dinero;

    fn sub(self, other: Dinero) -> Dinero {
        if self.moneda == other.moneda {
            if self.cantidad >= other.cantidad {
                Dinero {
                    cantidad: self.cantidad - other.cantidad,
                    moneda: self.moneda.clone(),
                }
            } else {
                panic!("No se puede tener una cantidad negativa de dinero.");
            }
        } else {
            panic!("Todavia no se ha implementado la resta de diferentes monedas.");
        }
    }
}

impl PartialEq for Dinero {
    fn eq(&self, other: &Self) -> bool {
        self.cantidad == other.cantidad && self.moneda == other.moneda
    }
}

impl PartialOrd for Dinero {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        if self.moneda == other.moneda {
            self.cantidad.partial_cmp(&other.cantidad)
        } else {
            panic!("Todavia no se ha implementado la comparación de diferentes monedas.");
        }
    }
}
