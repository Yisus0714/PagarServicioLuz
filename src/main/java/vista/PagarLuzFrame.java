package vista;

import controlador.PagarLuzControlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Cliente;
import modelo.Consumo;
import modelo.Recibo;

public class PagarLuzFrame extends JFrame {

    private JTextField txtServicio;
    private JList<Cliente> lstClientes;
    private JTextField txtTarjeta;
    private JButton btnPagar;
    private JButton btnNuevo;
    private JLabel lblMensaje;
    private JLabel lblTituloConsumo;
    private PagarLuzControlador controlador;

    private JLabel lblClienteValor;
    private JLabel lblServicioValor;
    private JLabel lblDireccionValor;
    private JLabel lblPeriodoValor;
    private JLabel lblConsumoValor;
    private JLabel lblFechaValor;
    private JLabel lblEstadoValor;
    private JLabel lblImporteValor;

    private final Color AZUL_OSCURO = new Color(23, 54, 93);
    private final Color AZUL = new Color(37, 99, 235);
    private final Color FONDO = new Color(241, 245, 249);
    private final Color BLANCO = Color.WHITE;
    private final Color TEXTO = new Color(40, 52, 70);
    private final Color GRIS = new Color(100, 116, 139);
    private final Color BORDE = new Color(220, 228, 238);
    private final Color VERDE = new Color(22, 163, 74);
    private final Color ROJO = new Color(220, 38, 38);

    public PagarLuzFrame() {
        super("Pago de servicio de luz");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Rectangle area = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
        setSize(Math.min(1050, area.width), Math.min(680, area.height));
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);

        construirInterfaz();
        reiniciarPanelInferior();
    }

    public void setControlador(PagarLuzControlador controlador) {
        this.controlador = controlador;
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(FONDO);

        JPanel pnlEncabezado = new JPanel(new BorderLayout());
        pnlEncabezado.setBackground(AZUL_OSCURO);
        pnlEncabezado.setBorder(
                BorderFactory.createEmptyBorder(12, 28, 12, 28)
        );

        JPanel pnlTitulos = new JPanel(new GridLayout(2, 1, 0, 3));
        pnlTitulos.setOpaque(false);

        JLabel lblTitulo = new JLabel("Pago de servicio de luz");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel(
                "Consulta tu recibo y realiza tu pago"
        );
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(210, 224, 244));

        pnlTitulos.add(lblTitulo);
        pnlTitulos.add(lblSubtitulo);
        pnlEncabezado.add(pnlTitulos, BorderLayout.CENTER);

        add(pnlEncabezado, BorderLayout.NORTH);

        JPanel pnlContenido = new JPanel(new BorderLayout(16, 0));
        pnlContenido.setBackground(FONDO);
        pnlContenido.setBorder(
                BorderFactory.createEmptyBorder(16, 22, 16, 22)
        );

        JPanel pnlIzquierda = new JPanel(new BorderLayout(0, 12));
        pnlIzquierda.setBackground(FONDO);

        JPanel pnlBusqueda = crearTarjeta();
        pnlBusqueda.setLayout(new BorderLayout(0, 8));
        pnlBusqueda.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE),
                        BorderFactory.createEmptyBorder(12, 16, 12, 16)
                )
        );

        JLabel lblServicio = new JLabel("Número de servicio");
        lblServicio.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblServicio.setForeground(AZUL_OSCURO);

        txtServicio = new JTextField();
        estilizarCampo(txtServicio);
        txtServicio.setToolTipText(
                "Escribe el número de servicio del cliente"
        );

        pnlBusqueda.add(lblServicio, BorderLayout.NORTH);
        pnlBusqueda.add(txtServicio, BorderLayout.CENTER);
        pnlIzquierda.add(pnlBusqueda, BorderLayout.NORTH);

        JPanel pnlConsumo = crearTarjeta();
        pnlConsumo.setLayout(new BorderLayout(0, 12));
        pnlConsumo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE),
                        BorderFactory.createEmptyBorder(14, 16, 14, 16)
                )
        );

        lblTituloConsumo = new JLabel("Detalle del consumo");
        lblTituloConsumo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTituloConsumo.setForeground(AZUL_OSCURO);

        pnlConsumo.add(lblTituloConsumo, BorderLayout.NORTH);

        JPanel pnlDatos = new JPanel(new GridLayout(4, 2, 10, 6));
        pnlDatos.setBackground(BLANCO);

        lblClienteValor = crearEtiquetaValor();
        lblServicioValor = crearEtiquetaValor();
        lblDireccionValor = crearEtiquetaValor();
        lblPeriodoValor = crearEtiquetaValor();
        lblConsumoValor = crearEtiquetaValor();
        lblFechaValor = crearEtiquetaValor();
        lblEstadoValor = crearEtiquetaValor();
        lblImporteValor = crearEtiquetaValor();

        pnlDatos.add(crearCampoInformacion("Cliente", lblClienteValor));
        pnlDatos.add(crearCampoInformacion("Número de servicio", lblServicioValor));
        pnlDatos.add(crearCampoInformacion("Dirección", lblDireccionValor));
        pnlDatos.add(crearCampoInformacion("Periodo", lblPeriodoValor));
        pnlDatos.add(crearCampoInformacion("Consumo", lblConsumoValor));
        pnlDatos.add(crearCampoInformacion("Fecha límite", lblFechaValor));
        pnlDatos.add(crearCampoInformacion("Estado del recibo", lblEstadoValor));
        pnlDatos.add(crearCampoInformacion("Importe", lblImporteValor));

        pnlConsumo.add(pnlDatos, BorderLayout.CENTER);
        pnlIzquierda.add(pnlConsumo, BorderLayout.CENTER);

        JPanel pnlPago = crearTarjeta();
        pnlPago.setLayout(new BorderLayout(0, 10));
        pnlPago.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE),
                        BorderFactory.createEmptyBorder(12, 16, 12, 16)
                )
        );

        JLabel lblTituloPago = new JLabel("Realizar pago");
        lblTituloPago.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTituloPago.setForeground(AZUL_OSCURO);

        JPanel pnlTarjeta = new JPanel(new BorderLayout(0, 5));
        pnlTarjeta.setOpaque(false);

        JLabel lblNumeroTarjeta = new JLabel("Número de tarjeta");
        lblNumeroTarjeta.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNumeroTarjeta.setForeground(TEXTO);

        txtTarjeta = new JTextField();
        estilizarCampo(txtTarjeta);
        txtTarjeta.setToolTipText("Ingresa el número de tarjeta (16 dígitos)");

        pnlTarjeta.add(lblNumeroTarjeta, BorderLayout.NORTH);
        pnlTarjeta.add(txtTarjeta, BorderLayout.CENTER);

        btnPagar = new JButton("Pagar ahora");
        estilizarBotonPrincipal(btnPagar);

        btnNuevo = new JButton("Nuevo pago");
        estilizarBotonSecundario(btnNuevo);

        JPanel pnlBotones = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        pnlBotones.setOpaque(false);
        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnPagar);

        JPanel pnlParteInferior = new JPanel(new BorderLayout(12, 0));
        pnlParteInferior.setOpaque(false);
        pnlParteInferior.add(pnlTarjeta, BorderLayout.CENTER);
        pnlParteInferior.add(pnlBotones, BorderLayout.EAST);

        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMensaje.setForeground(GRIS);

        JPanel pnlZonaPago = new JPanel(new BorderLayout(0, 8));
        pnlZonaPago.setOpaque(false);
        pnlZonaPago.add(lblTituloPago, BorderLayout.NORTH);
        pnlZonaPago.add(pnlParteInferior, BorderLayout.CENTER);
        pnlZonaPago.add(lblMensaje, BorderLayout.SOUTH);

        pnlPago.add(pnlZonaPago, BorderLayout.CENTER);
        pnlIzquierda.add(pnlPago, BorderLayout.SOUTH);

        pnlContenido.add(pnlIzquierda, BorderLayout.CENTER);

        JPanel pnlClientes = crearTarjeta();
        pnlClientes.setPreferredSize(new Dimension(290, 0));
        pnlClientes.setLayout(new BorderLayout(0, 12));
        pnlClientes.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE),
                        BorderFactory.createEmptyBorder(16, 13, 13, 13)
                )
        );

        JPanel pnlTituloClientes = new JPanel(new BorderLayout(0, 6));
        pnlTituloClientes.setOpaque(false);

        JLabel lblClientes = new JLabel("Clientes");
        lblClientes.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblClientes.setForeground(AZUL_OSCURO);

        JLabel lblAyuda = new JLabel(
                "<html>Selecciona un cliente para consultar su recibo.</html>"
        );
        lblAyuda.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblAyuda.setForeground(GRIS);

        pnlTituloClientes.add(lblClientes, BorderLayout.NORTH);
        pnlTituloClientes.add(lblAyuda, BorderLayout.CENTER);

        lstClientes = new JList<>();
        lstClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstClientes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lstClientes.setBackground(BLANCO);
        lstClientes.setForeground(TEXTO);
        lstClientes.setSelectionBackground(new Color(219, 234, 254));
        lstClientes.setSelectionForeground(AZUL_OSCURO);
        lstClientes.setFixedCellHeight(56);
        lstClientes.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        lstClientes.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista,
                    Object valor,
                    int indice,
                    boolean seleccionado,
                    boolean tieneFoco
            ) {
                JLabel etiqueta = (JLabel) super.getListCellRendererComponent(
                        lista, valor, indice, seleccionado, tieneFoco
                );

                etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 13));

                if (valor instanceof Cliente) {
                    Cliente c = (Cliente) valor;
                    etiqueta.setText(
                            "<html><b>" + c.getNombre() + "</b><br>"
                                    + c.getNumeroServicio() + "</html>"
                    );
                }

                etiqueta.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(
                                        0, 0, 1, 0, BORDE
                                ),
                                BorderFactory.createEmptyBorder(8, 8, 8, 8)
                        )
                );

                return etiqueta;
            }
        });

        JScrollPane scrollClientes = new JScrollPane(lstClientes);
        scrollClientes.setBorder(BorderFactory.createLineBorder(BORDE));

        pnlClientes.add(pnlTituloClientes, BorderLayout.NORTH);
        pnlClientes.add(scrollClientes, BorderLayout.CENTER);

        pnlContenido.add(pnlClientes, BorderLayout.EAST);

        add(pnlContenido, BorderLayout.CENTER);

        txtServicio.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        buscar();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        buscar();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        buscar();
                    }
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

    private JPanel crearTarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(BLANCO);
        return panel;
    }

    private JLabel crearEtiquetaValor() {
        JLabel etiqueta = new JLabel("—");
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        etiqueta.setForeground(TEXTO);
        etiqueta.setVerticalAlignment(SwingConstants.CENTER);
        return etiqueta;
    }

    private JPanel crearCampoInformacion(String titulo, JLabel valor) {
        JPanel panel = new JPanel(new BorderLayout(0, 1));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE),
                        BorderFactory.createEmptyBorder(3, 10, 3, 10)
                )
        );

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTitulo.setForeground(GRIS);

        valor.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(valor, BorderLayout.CENTER);

        return panel;
    }
    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setForeground(TEXTO);
        campo.setBackground(BLANCO);
        campo.setCaretColor(AZUL);
        campo.setPreferredSize(new Dimension(180, 36));
        campo.setMargin(new Insets(5, 9, 5, 9));
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDE),
                        BorderFactory.createEmptyBorder(3, 7, 3, 7)
                )
        );
    }

    private void estilizarBotonPrincipal(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(BLANCO);
        boton.setBackground(AZUL);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(115, 38));
    }

    private void estilizarBotonSecundario(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(AZUL_OSCURO);
        boton.setBackground(new Color(232, 238, 246));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(105, 38));
    }

    private void buscar() {
        if (controlador != null) {
            controlador.buscarClientes(txtServicio.getText());
        }
    }

    private void reiniciarPanelInferior() {
        lblTituloConsumo.setText("Detalle del consumo");

        lblClienteValor.setText("—");
        lblServicioValor.setText("—");
        lblDireccionValor.setText("—");
        lblPeriodoValor.setText("—");
        lblConsumoValor.setText("—");
        lblFechaValor.setText("—");
        lblEstadoValor.setText("Sin seleccionar");
        lblImporteValor.setText("$0.00");

        lblEstadoValor.setForeground(GRIS);
        lblImporteValor.setForeground(AZUL_OSCURO);

        txtTarjeta.setText("");
        txtTarjeta.setEnabled(false);
        btnPagar.setEnabled(false);

        lblMensaje.setForeground(GRIS);
        lblMensaje.setText(" ");
    }

    public void mostrarClientes(List<Cliente> clientes) {
        lstClientes.setListData(clientes.toArray(new Cliente[0]));

        reiniciarPanelInferior();

        if (clientes.isEmpty() && !txtServicio.getText().trim().isEmpty()) {
            lblMensaje.setForeground(GRIS);
            lblMensaje.setText("No se encontraron clientes.");
        }
    }

    public void mostrarConsumo(Consumo consumo) {
        Cliente cliente = lstClientes.getSelectedValue();

        txtTarjeta.setText("");
        lblMensaje.setText(" ");
        lblTituloConsumo.setText("Detalle del consumo");

        if (consumo == null || cliente == null) {
            reiniciarPanelInferior();
            lblEstadoValor.setText("Sin información");
            return;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblClienteValor.setText(cliente.getNombre());
        lblServicioValor.setText(cliente.getNumeroServicio());
        lblDireccionValor.setText(cliente.getDireccion());
        lblPeriodoValor.setText(consumo.getPeriodo());
        lblConsumoValor.setText(String.format("%.0f kWh", consumo.getKwh()));
        lblFechaValor.setText(consumo.getFechaLimite().format(formato));
        lblImporteValor.setText(String.format("$%.2f", consumo.getImporte()));

        if (consumo.isPagado()) {
            lblEstadoValor.setText("PAGADO");
            lblEstadoValor.setForeground(VERDE);

            txtTarjeta.setEnabled(false);
            btnPagar.setEnabled(false);

            lblMensaje.setForeground(VERDE);
            lblMensaje.setText("Este servicio ya está pagado.");
        } else {
            lblEstadoValor.setText("PENDIENTE");
            lblEstadoValor.setForeground(ROJO);

            txtTarjeta.setEnabled(true);
            btnPagar.setEnabled(true);
        }
    }

    public void mostrarRecibo(Recibo recibo) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter soloFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String tarjeta = recibo.getPago().getTarjeta();
        String enmascarada = "**** **** **** " + tarjeta.substring(12);

        lblTituloConsumo.setText("Recibo de pago");

        lblClienteValor.setText(recibo.getCliente().getNombre());
        lblServicioValor.setText(recibo.getCliente().getNumeroServicio());
        lblDireccionValor.setText(recibo.getCliente().getDireccion());
        lblPeriodoValor.setText(recibo.getConsumo().getPeriodo());
        lblConsumoValor.setText(
                String.format("%.0f kWh", recibo.getConsumo().getKwh())
        );
        lblFechaValor.setText(
                recibo.getConsumo().getFechaLimite().format(soloFecha)
        );
        lblEstadoValor.setText(
                "PAGADO - Folio " + String.format("%06d", recibo.getFolio())
        );
        lblEstadoValor.setForeground(VERDE);
        lblImporteValor.setText(
                String.format("$%.2f", recibo.getPago().getImporte())
        );

        txtTarjeta.setText(enmascarada);
        txtTarjeta.setEnabled(false);
        btnPagar.setEnabled(false);

        lblMensaje.setForeground(VERDE);
        lblMensaje.setText(
                "Pago realizado con éxito el "
                        + recibo.getPago().getFecha().format(formato) + "."
        );
    }

    public void mostrarError(String mensaje) {
        lblMensaje.setForeground(ROJO);
        lblMensaje.setText(mensaje);
    }

    public void limpiar() {
        txtServicio.setText("");
        lstClientes.setListData(new Cliente[0]);
        reiniciarPanelInferior();
    }
}