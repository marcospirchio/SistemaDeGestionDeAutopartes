package tp;

import java.util.Date;

public class VentaEfectivo extends Venta {
    private static final long serialVersionUID = 1L;

    public VentaEfectivo(String id, String pedidoId, Date fecha, String nombreProducto, int cantidad, double montoTotal) {
        super(id, pedidoId, fecha, nombreProducto, cantidad, montoTotal, "Efectivo");
        // Descuento 10% en efectivo
        this.montoTotal *= 0.9;
    }

    @Override
    public void procesarPago() {
        System.out.println("Procesando pago en efectivo.");
    }
}

