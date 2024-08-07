package tp;

import java.io.Serializable;

public class DetallePedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idAutoparte;
    private String idPedido;
    private int cantidad;
    private double precioUnitario;
    private double precioTotal;

    public DetallePedido(String idAutoparte, String idPedido, int cantidad, double precioUnitario, double precioTotal) {
        this.idAutoparte = idAutoparte;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
    }

    // Getters y Setters
    public String getIdAutoparte() {
        return idAutoparte;
    }

    public void setIdAutoparte(String idAutoparte) {
        this.idAutoparte = idAutoparte;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
