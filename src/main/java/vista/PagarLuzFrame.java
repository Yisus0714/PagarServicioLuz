package vista;

import controlador.PagarLuzControlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Cliente;
import modelo.Consumo;
import modelo.Recibo;

public class PagarLuzFrame extends JFrame {
    private JTextField txtServicio;
    private JList<Cliente> lstClientes;
    private JPanel pnlInferior;
    private JTextField txtTarjeta;
    private JButton btnPagar;
    private JButton btnNuevo;
    private JTextArea txaDetalle;
    private JLabel lblMensaje;
    private PagarLuzControlador controlador;

    public PagarLuzFrame() {
        super("Pago de servicio de luz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 500);
        setLocationRelativeTo(null);
        construirInterfaz();
        reiniciarPanelInferior();
    }

    public void setControlador(PagarLuzControlador controlador) {
        this.controlador = controlador;
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Zona izquierda
        JPanel pnlIzquierda = new JPanel(new BorderLayout(10, 10));

        JPanel pnlServicio = new JPanel(new BorderLayout(8, 0));
        pnlServicio.add(new JLabel("Número de servicio:"), BorderLayout.WEST);
        txtServicio = new JTextField();
        pnlServicio.add(txtServicio, BorderLayout.CENTER);
        pnlIzquierda.add(pnlServicio, BorderLayout.NORTH);

        pnlInferior = new JPanel(new BorderLayout());
        pnlInferior.setBorder(BorderFactory.createTitledBorder("Datos de consumo"));
        txaDetalle = new JTextArea();
        txaDetalle.setEditable(false);
        txaDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        pnlInferior.add(new JScrollPane(txaDetalle), BorderLayout.CENTER);
        pnlIzquierda.add(pnlInferior, BorderLayout.CENTER);

        JPanel pnlPago = new JPanel(new BorderLayout(0, 5));
        JPanel pnlControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlControles.add(new JLabel("Número de tarjeta:"));
        txtTarjeta = new JTextField(16);
        pnlControles.add(txtTarjeta);
        btnPagar = new JButton("Pagar");
        pnlControles.add(btnPagar);
        btnNuevo = new JButton("Nuevo pago");
        pnlControles.add(btnNuevo);
        pnlPago.add(pnlControles, BorderLayout.NORTH);
        lblMensaje = new JLabel(" ");
        pnlPago.add(lblMensaje, BorderLayout.SOUTH);
        pnlIzquierda.add(pnlPago, BorderLayout.SOUTH);

        add(pnlIzquierda, BorderLayout.CENTER);

        // Zona derecha
        lstClientes = new JList<>();
        JScrollPane scrollClientes = new JScrollPane(lstClientes);
        scrollClientes.setBorder(BorderFactory.createTitledBorder("Clientes"));
        scrollClientes.setPreferredSize(new Dimension(380, 0));
        add(scrollClientes, BorderLayout.EAST);

        // Eventos
        txtServicio.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { buscar(); }
            @Override public void removeUpdate(DocumentEvent e) { buscar(); }
            @Override public void changedUpdate(DocumentEvent e) { buscar(); }
        });

        lstClientes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && controlador != null) {
                Cliente seleccionado = lstClientes.getSelectedValue();
                if (seleccionado != null) {
                    controlador.seleccionarCliente(seleccionado);
                }
            }
        });

        btnPagar.addActionListener(e -> {
            if (controlador != null) {
                controlador.pagar(txtTarjeta.getText().trim());
            }
        });

        btnNuevo.addActionListener(e -> {
            if (controlador != null) {
                controlador.nuevoPago();
            }
        });
    }

    private void buscar() {
        if (controlador != null) {
            controlador.buscarClientes(txtServicio.getText());
        }
    }

    private void reiniciarPanelInferior() {
        ((TitledBorder) pnlInferior.getBorder()).setTitle("Datos de consumo");
        pnlInferior.repaint();
        txaDetalle.setText("Seleccione un cliente");
        txtTarjeta.setText("");
        txtTarjeta.setEnabled(false);
        btnPagar.setEnabled(false);
        lblMensaje.setText(" ");
    }

    public void mostrarClientes(List<Cliente> clientes) {
        lstClientes.setListData(clientes.toArray(new Cliente[0]));
        reiniciarPanelInferior();
        if (clientes.isEmpty() && !txtServicio.getText().trim().isEmpty()) {
            lblMensaje.setForeground(Color.GRAY);
            lblMensaje.setText("Sin resultados");
        }
    }

    public void mostrarConsumo(Consumo consumo) {
        Cliente c = lstClientes.getSelectedValue();
        ((TitledBorder) pnlInferior.getBorder()).setTitle("Datos de consumo");
        pnlInferior.repaint();
        lblMensaje.setText(" ");
        txtTarjeta.setText("");

        if (consumo == null || c == null) {
            txaDetalle.setText("No hay información de consumo.");
            txtTarjeta.setEnabled(false);
            btnPagar.setEnabled(false);
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente:       ").append(c.getNombre()).append("\n");
        sb.append("Dirección:     ").append(c.getDireccion()).append("\n");
        sb.append("No. servicio:  ").append(c.getNumeroServicio()).append("\n");
        sb.append("Periodo:       ").append(consumo.getPeriodo()).append("\n");
        sb.append("Consumo:       ").append(String.format("%.0f kWh", consumo.getKwh())).append("\n");
        sb.append("Importe:       ").append(String.format("$%.2f", consumo.getImporte())).append("\n");
        sb.append("Fecha límite:  ").append(consumo.getFechaLimite().format(fmt)).append("\n");

        if (consumo.isPagado()) {
            sb.append("\nSin adeudo pendiente");
            txtTarjeta.setEnabled(false);
            btnPagar.setEnabled(false);
        } else {
            txtTarjeta.setEnabled(true);
            btnPagar.setEnabled(true);
        }
        txaDetalle.setText(sb.toString());
    }

    public void mostrarRecibo(Recibo recibo) {
        ((TitledBorder) pnlInferior.getBorder()).setTitle("Recibo de pago");
        pnlInferior.repaint();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String tarjeta = recibo.getPago().getTarjeta();
        String enmascarada = "**** **** **** " + tarjeta.substring(12);

        StringBuilder sb = new StringBuilder();
        sb.append("Folio:         ").append(String.format("%06d", recibo.getFolio())).append("\n");
        sb.append("Fecha y hora:  ").append(recibo.getPago().getFecha().format(fmt)).append("\n");
        sb.append("Cliente:       ").append(recibo.getCliente().getNombre()).append("\n");
        sb.append("No. servicio:  ").append(recibo.getCliente().getNumeroServicio()).append("\n");
        sb.append("Periodo:       ").append(recibo.getConsumo().getPeriodo()).append("\n");
        sb.append("Consumo:       ").append(String.format("%.0f kWh", recibo.getConsumo().getKwh())).append("\n");
        sb.append("Importe:       ").append(String.format("$%.2f", recibo.getPago().getImporte())).append("\n");
        sb.append("Tarjeta:       ").append(enmascarada).append("\n");
        sb.append("\nPago realizado con éxito");
        txaDetalle.setText(sb.toString());

        txtTarjeta.setEnabled(false);
        btnPagar.setEnabled(false);
        lblMensaje.setForeground(new Color(0, 130, 0));
        lblMensaje.setText("Pago realizado con éxito");
    }

    public void mostrarError(String mensaje) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(mensaje);
    }

    public void limpiar() {
        txtServicio.setText("");
        lstClientes.setListData(new Cliente[0]);
        reiniciarPanelInferior();
    }
}