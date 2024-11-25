use std::ops::ControlFlow;

use num_format::{Locale, ToFormattedString};
const LOCALE_POR_DEFECTO: Locale = Locale::es;
use rand::Rng;

mod cuenta;
mod dinero;

use cuenta::Cuenta;
use dinero::Dinero;

const CUANTAS_CUENTAS: usize = 100;
const CUANTOS_HILOS_DE_EJECUCION: usize = 12;
const CUANTAS_TRANSFERENCIAS_POR_HILO: usize = 33;

fn main() {
    let SALDO_INICIAL: Dinero = Dinero::new(1000_00); // la moneda por defecto es eurocent

    println!("Banco: creando e inicializando cuentas...");
    let lista_de_cuentas_activas = std::sync::Arc::new(std::sync::Mutex::new(Vec::new()));
    for _ in 0..CUANTAS_CUENTAS {
        lista_de_cuentas_activas
            .lock()
            .unwrap()
            .push(Cuenta::new(&SALDO_INICIAL));
    }
    println!("Banco: ...se han preparado {CUANTAS_CUENTAS} cuentas, con un saldo inicial de {SALDO_INICIAL} cada una.");
    imprimir_suma_de_saldos(&lista_de_cuentas_activas.lock().unwrap());

    println!("Banco: INICIO de la simulación. Se van a realizar solo transacciones internas entre cuentas del banco.");

    let mut lista_de_hilos_de_ejecucion = Vec::new();
    for num_hilo in 1..CUANTOS_HILOS_DE_EJECUCION {
        let lista_de_cuentas_activas = std::sync::Arc::clone(&lista_de_cuentas_activas);
        let hilo = std::thread::spawn(move || {
            for _ in 1..CUANTAS_TRANSFERENCIAS_POR_HILO {
                let indice_origen = rand::thread_rng().gen_range(0..CUANTAS_CUENTAS);
                let indice_destino = rand::thread_rng().gen_range(0..CUANTAS_CUENTAS);
                let cantidad_a_transferir = rand::thread_rng().gen_range(0..1000_00);
                transferir_entre_cuentas_internas(
                    format!("hilo {num_hilo}").as_str(),
                    &mut lista_de_cuentas_activas.lock().unwrap(),
                    indice_origen,
                    indice_destino,
                    cantidad_a_transferir,
                );
            }
        });
        lista_de_hilos_de_ejecucion.push(hilo);
    }
    for hilo in lista_de_hilos_de_ejecucion {
        hilo.join().unwrap();
    }

    println!("Banco: FIN de la simulación.");
    imprimir_suma_de_saldos(&lista_de_cuentas_activas.lock().unwrap());
}

fn transferir_entre_cuentas_internas(
    quien_transfiere: &str,
    lista_de_cuentas_activas: &mut Vec<Cuenta>,
    indice_origen: usize,
    indice_destino: usize,
    cantidad_a_transferir: u64,
) {
    let cuenta_origen = lista_de_cuentas_activas.get_mut(indice_origen).unwrap();
    match cuenta_origen.retirar(Dinero::new(cantidad_a_transferir)) {
        Ok(_) => {
            let cuenta_destino = lista_de_cuentas_activas.get_mut(indice_destino).unwrap();
            cuenta_destino.depositar(Dinero::new(cantidad_a_transferir));
            println!("  {quien_transfiere}: transferidos {cantidad_a_transferir} de la cuenta {indice_origen} a la cuenta {indice_destino}");
        }
        Err(error) => {
            println!(
                "  {quien_transfiere}: error al retirar dinero de la cuenta {indice_origen}: {}",
                error.mensaje
            );
        }
    }
}

fn imprimir_suma_de_saldos(lista_de_cuentas: &Vec<Cuenta>) {
    let suma_total: u64 = lista_de_cuentas
        .iter()
        .map(|cuenta| cuenta.get_saldo().get_cantidad())
        .sum();
    println!(
        "La suma total de todos los saldos es: {} {}",
        suma_total.to_formatted_string(&LOCALE_POR_DEFECTO),
        Dinero::MONEDA_POR_DEFECTO
    );
}
