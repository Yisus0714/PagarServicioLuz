package modelo;

import java.time.LocalDate;

public class Consumo {
    private String periodo;
    private double kwh;
    private double importe;
    private LocalDate fechaLimite;
    private boolean pagado;

    public Consumo(String periodo, double kwh, double importe, LocalDate fechaLimite) {
        this.periodo = periodo;
        this.kwh = kwh;
        this.importe = importe;
        this.fechaLimite = fechaLimite;
        this.pagado = false;
    }

    public String getPeriodo() { return periodo; }
    public double getKwh() { return kwh; }
    public double getImporte() { return importe; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public boolean isPagado() { return pagado; }
    public void setPagado(boolean pagado) { this.pagado = pagado; }
}