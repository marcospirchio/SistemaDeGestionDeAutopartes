package tp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String clienteId;
    private Date fecha;
    private double total;
    private List<DetallePedido> detalles; 

    public Pedido(String id, String clienteId, Date fecha, double total, List<DetallePedido> detalles) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.detalles = detalles; 
    }

    public String getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public Date getFecha() {
        return fecha;	
    }

    public double getTotal() {
        return total;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id + ", Fecha: " + fecha + ", Total: $" + total;
    }
}
