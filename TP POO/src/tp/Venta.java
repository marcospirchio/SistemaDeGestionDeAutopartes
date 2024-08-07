package tp;

import java.io.Serializable;
import java.util.Date;

public abstract class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String pedidoId;
    protected Date fecha;
    protected String nombreProducto;
    protected int cantidad;
    protected double montoTotal;
    protected String metodoPago;

    public Venta(String id, String pedidoId, Date fecha, String nombreProducto, int cantidad, double montoTotal, String metodoPago) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
    }

    public String getId() {
        return id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public abstract void procesarPago();
}
