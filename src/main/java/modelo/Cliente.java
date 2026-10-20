package modelo;

public class Cliente {
    private String numeroServicio;
    private String nombre;
    private String direccion;

    public Cliente(String numeroServicio, String nombre, String direccion) {
        this.numeroServicio = numeroServicio;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNumeroServicio() { return numeroServicio; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        return numeroServicio + " - " + nombre + " - " + direccion;
    }
}