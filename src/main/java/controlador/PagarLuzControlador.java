package controlador;

import java.util.List;
import modelo.Cliente;
import modelo.Consumo;
import modelo.Recibo;
import modelo.ServicioLuzBO;
import vista.PagarLuzFrame;

public class PagarLuzControlador {
    private PagarLuzFrame vista;
    private ServicioLuzBO negocio;
    private Cliente clienteSeleccionado;
    private Consumo consumoActual;

    public PagarLuzControlador(PagarLuzFrame vista, ServicioLuzBO negocio) {
        this.vista = vista;
        this.negocio = negocio;
    }

    public void buscarClientes(String criterio) {
        List<Cliente> clientes = negocio.buscarClientes(criterio);
        vista.mostrarClientes(clientes);
    }

    public void seleccionarCliente(Cliente cliente) {
        clienteSeleccionado = cliente;
        consumoActual = negocio.obtenerConsumo(cliente);
        vista.mostrarConsumo(consumoActual);
    }

    public void pagar(String tarjeta) {
        if (clienteSeleccionado == null || consumoActual == null) {
            return;
        }
        try {
            Recibo recibo = negocio.procesarPago(clienteSeleccionado, consumoActual, tarjeta);
            vista.mostrarRecibo(recibo);
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void nuevoPago() {
        clienteSeleccionado = null;
        consumoActual = null;
        vista.limpiar();
    }
}