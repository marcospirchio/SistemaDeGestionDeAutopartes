	package tp;
	
	import java.util.Date;
	
	public class VentaDebito extends Venta {
	    private static final long serialVersionUID = 1L;
	
	    public VentaDebito(String id, String pedidoId, Date fecha, String nombreProducto, int cantidad, double montoTotal) {
	        super(id, pedidoId, fecha, nombreProducto, cantidad, montoTotal, "Débito");
	    }
	
	    @Override
	    public void procesarPago() {
	        System.out.println("Procesando pago con tarjeta de débito.");
	    }
	}

