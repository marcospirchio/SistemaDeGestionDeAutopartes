package tp;
import java.util.Date;

public class VentaCredito extends Venta {
    private static final long serialVersionUID = 1L;
    
    private int cuotas;

    public VentaCredito(String id, String pedidoId, Date fecha, String nombreProducto, int cantidad, double montoTotal, int cuotas) {
        super(id, pedidoId, fecha, nombreProducto, cantidad, montoTotal, "Crédito");
        this.cuotas = cuotas;
        aplicarRecargoPorCuotas();
    }

    private void aplicarRecargoPorCuotas() {
        switch (cuotas) {
            case 2:
                montoTotal *= 1.06; // Recargo del 6% con 2 cuotas
                break;
            case 3:
                montoTotal *= 1.12; // Recargo del 12% con 3 cuotas
                break;
            case 6:
                montoTotal *= 1.20; // Recargo del 20% con 6 cuotas
                break;
            default:
                throw new IllegalArgumentException("Número de cuotas inválido");
        }
    }

    @Override
    public void procesarPago() {
        System.out.println("Procesando pago con tarjeta de crédito en " + cuotas + " cuotas.");
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
        aplicarRecargoPorCuotas();
    }
}



