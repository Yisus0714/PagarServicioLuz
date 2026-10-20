package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioLuzBO {
    private List<Cliente> clientes;
    private Map<String, Consumo> consumos;
    private int siguienteFolio;

    public ServicioLuzBO() {
        clientes = new ArrayList<>();
        consumos = new HashMap<>();
        siguienteFolio = 1;
        cargarDatosEjemplo();
    }

    private void cargarDatosEjemplo() {
        agregar("100200300401", "María López Pérez", "Calle Sonora 123, Cd. Obregón",
                "Ago-Sep 2026", 320, 685.50);
        agregar("100200300402", "José Ramírez Soto", "Av. Miguel Alemán 456, Cd. Obregón",
                "Ago-Sep 2026", 540, 1210.80);
        agregar("100200311503", "Ana Torres Vega", "Calle Náinari 78, Cd. Obregón",
                "Ago-Sep 2026", 210, 430.25);
        agregar("200300400504", "Luis Hernández Cruz", "Blvd. Kino 910, Cd. Obregón",
                "Ago-Sep 2026", 760, 1895.00);
    }

    private void agregar(String servicio, String nombre, String direccion,
                         String periodo, double kwh, double importe) {
        clientes.add(new Cliente(servicio, nombre, direccion));
        consumos.put(servicio, new Consumo(periodo, kwh, importe, LocalDate.of(2026, 11, 15)));
    }

    public List<Cliente> buscarClientes(String criterio) {
        List<Cliente> resultado = new ArrayList<>();
        if (criterio == null || criterio.trim().isEmpty()) {
            return resultado;
        }
        for (Cliente c : clientes) {
            if (c.getNumeroServicio().contains(criterio.trim())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public Consumo obtenerConsumo(Cliente cliente) {
        return consumos.get(cliente.getNumeroServicio());
    }

    public boolean validarTarjeta(String tarjeta) {
        return tarjeta != null && tarjeta.matches("\\d{16}");
    }

    public Recibo procesarPago(Cliente cliente, Consumo consumo, String tarjeta) {
        if (!validarTarjeta(tarjeta)) {
            throw new IllegalArgumentException("Tarjeta inválida, verifique el número");
        }
        Pago pago = new Pago(tarjeta, LocalDateTime.now(), consumo.getImporte());
        consumo.setPagado(true);
        return new Recibo(siguienteFolio++, cliente, consumo, pago);
    }
}