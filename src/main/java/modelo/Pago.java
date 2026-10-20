package modelo;

import java.time.LocalDateTime;

public class Pago {
    private String tarjeta;
    private LocalDateTime fecha;
    private double importe;

    public Pago(String tarjeta, LocalDateTime fecha, double importe) {
        this.tarjeta = tarjeta;
        this.fecha = fecha;
        this.importe = importe;
    }

    public String getTarjeta() { return tarjeta; }
    public LocalDateTime getFecha() { return fecha; }
    public double getImporte() { return importe; }
}