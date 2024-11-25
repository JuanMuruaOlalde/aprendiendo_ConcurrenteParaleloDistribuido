use crate::dinero::Dinero;

pub struct Cuenta {
    id: uuid::Uuid,
    saldo: Dinero,
}

#[derive(Debug)]
pub struct ErrorDeOperacion {
    pub mensaje: &'static str,
    pub cantidad_con_la_que_se_pretendia_operar: Dinero,
    pub saldo_existente: Dinero,
}

impl Cuenta {
    pub fn new(saldo_inicial: &Dinero) -> Cuenta {
        Cuenta {
            id: uuid::Uuid::now_v7(),
            saldo: saldo_inicial.clone(),
        }
    }

    pub fn get_saldo(&self) -> Dinero {
        self.saldo.clone()
    }

    pub fn depositar(&mut self, cantidad: Dinero) -> Dinero {
        self.saldo = self.saldo.clone() + cantidad;
        self.get_saldo()
    }

    pub fn retirar(&mut self, cantidad: Dinero) -> Result<Dinero, ErrorDeOperacion> {
        if self.saldo >= cantidad {
            self.saldo = self.saldo.clone() - cantidad;
            Ok(self.get_saldo())
        } else {
            Err(ErrorDeOperacion {
                mensaje: "saldo insuficiente",
                cantidad_con_la_que_se_pretendia_operar: cantidad,
                saldo_existente: self.saldo.clone(),
            })
        }
    }
}

impl std::fmt::Display for Cuenta {
    fn fmt(&self, f: &mut std::fmt::Formatter) -> std::fmt::Result {
        write!(f, "Cuenta {} con saldo: {}", self.id, self.saldo)
    }
}
