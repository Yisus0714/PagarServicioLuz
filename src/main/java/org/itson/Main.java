package org.itson;

import controlador.PagarLuzControlador;
import modelo.Cliente;
import modelo.Consumo;
import modelo.Recibo;
import modelo.ServicioLuzBO;
import vista.PagarLuzFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PagarLuzFrame vista = new PagarLuzFrame();
            ServicioLuzBO negocio = new ServicioLuzBO();
            PagarLuzControlador controlador = new PagarLuzControlador(vista, negocio);
            vista.setControlador(controlador);
            vista.setVisible(true);
        });
    }
}