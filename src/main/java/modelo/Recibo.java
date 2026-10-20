package modelo;

public class Recibo {
    private int folio;
    private Cliente cliente;
    private Consumo consumo;
    private Pago pago;

    public Recibo(int folio, Cliente cliente, Consumo consumo, Pago pago) {
        this.folio = folio;
        this.cliente = cliente;
        this.consumo = consumo;
        this.pago = pago;
    }

    public int getFolio() { return folio; }
    public Cliente getCliente() { return cliente; }
    public Consumo getConsumo() { return consumo; }
    public Pago getPago() { return pago; }
}
