package tp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogoPedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Pedido> pedidos;
    private List<Venta> ventas; 
    private CatalogoAutopartes catalogoAutopartes; 

    public CatalogoPedidos() {
        pedidos = new ArrayList<>();
        ventas = new ArrayList<>(); 
        catalogoAutopartes = new CatalogoAutopartes();
    }

    // Constructor para inicialización durante la deserialización
    public CatalogoPedidos(List<Pedido> pedidos, List<Venta> ventas, CatalogoAutopartes catalogoAutopartes) {
        this.pedidos = pedidos != null ? pedidos : new ArrayList<>();
        this.ventas = ventas != null ? ventas : new ArrayList<>(); 
        this.catalogoAutopartes = catalogoAutopartes != null ? catalogoAutopartes : new CatalogoAutopartes(); 
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    public void agregarVenta(Venta venta) {
        if (ventas == null) {
            ventas = new ArrayList<>();
        }
        ventas.add(venta);
    }

    public List<Venta> getVentas() {
        return new ArrayList<>(ventas);
    }

    public List<Pedido> buscarPedidosPorClienteId(String clienteId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getClienteId().equals(clienteId)) {
                resultado.add(pedido);
            }
        }
        return resultado;
    }

    public List<Pedido> getPedidosPorCliente(String idCliente) {
        List<Pedido> pedidosCliente = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getClienteId().equals(idCliente)) {
                pedidosCliente.add(pedido);
            }
        }
        return pedidosCliente;
    }

    public void actualizarPedido(Pedido pedidoActualizado) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId().equals(pedidoActualizado.getId())) {
                pedidos.set(i, pedidoActualizado);
                return;
            }
        }
    }

    public void eliminarPedido(String pedidoId) {
        pedidos.removeIf(pedido -> pedido.getId().equals(pedidoId));
    }

    public static CatalogoPedidos cargarCatalogo(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (CatalogoPedidos) ois.readObject();
        }
    }

    public void guardarCatalogo(String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
        }
    }

    public void cancelarPedido(String pedidoId) {
        Pedido pedidoACancelar = null;
        for (Pedido pedido : pedidos) {
            if (pedido.getId().equals(pedidoId)) {
                pedidoACancelar = pedido;
                break;
            }
        }

        if (pedidoACancelar != null) {
            // Restablece el stock de las autopartes reservadas al cancelar el pedido 
            for (DetallePedido detalle : pedidoACancelar.getDetalles()) {
                Autoparte autoparte = catalogoAutopartes.buscarAutoparte(detalle.getIdAutoparte());
                if (autoparte != null) {
                    autoparte.setStock(autoparte.getStock() + detalle.getCantidad()); 
                    catalogoAutopartes.actualizarAutoparte(autoparte);
                }
            }

            eliminarPedido(pedidoId); // Eliminar el pedido del catálogo de pedidos
            try {
                guardarCatalogo("catalogo_pedidos.dat"); // Guardar cambios en el catálogo de pedidos
            } catch (IOException e) {
                // Manejo de cualquier error
                e.printStackTrace();
            }
        }
    }

    public CatalogoAutopartes getCatalogoAutopartes() {
        return catalogoAutopartes;
    }

    public void setCatalogoAutopartes(CatalogoAutopartes catalogoAutopartes) {
        this.catalogoAutopartes = catalogoAutopartes;
    }

    // para depuración y verificar el estado de las listas
    public void printDebugInfo() {
        System.out.println("Pedidos: " + pedidos.size());
        System.out.println("Ventas: " + (ventas == null ? "null" : ventas.size()));
    }
}

