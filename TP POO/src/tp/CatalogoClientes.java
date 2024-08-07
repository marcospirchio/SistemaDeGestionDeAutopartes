package tp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogoClientes implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Cliente> clientes;

    public CatalogoClientes() {
        this.clientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarClientePorId(String id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    public static CatalogoClientes cargarCatalogo(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (CatalogoClientes) ois.readObject();
        }
    }

    public void guardarCatalogo(String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
        }
    }
}
