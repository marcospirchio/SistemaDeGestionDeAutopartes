package tp;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;


public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private CatalogoAutopartes catalogoAutopartes;
    private CatalogoClientes catalogoClientes;
    private CatalogoPedidos catalogoPedidos;
    private Cliente clienteActual;
    private boolean adminLoggedIn = false;
    private boolean clienteLoggedIn = false;
    private static final String ARCHIVO_AUTOPARTES = "catalogo_autopartes.bin";
    private static final String ARCHIVO_CLIENTES = "catalogo_clientes.bin";
    private static final String ARCHIVO_PEDIDOS = "catalogo_pedidos.bin";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");


    public Main() {
        // Cargar catálogos desde archivos
        try {
            catalogoAutopartes = CatalogoAutopartes.cargarCatalogo(ARCHIVO_AUTOPARTES);
            catalogoClientes = CatalogoClientes.cargarCatalogo(ARCHIVO_CLIENTES);
            catalogoPedidos = CatalogoPedidos.cargarCatalogo(ARCHIVO_PEDIDOS);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            catalogoAutopartes = new CatalogoAutopartes();
            catalogoClientes = new CatalogoClientes();
            catalogoPedidos = new CatalogoPedidos();
        }

        setTitle("Sistema de Gestión de Autopartes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel principal de botones
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 3));

        JButton btnLoginCliente = new JButton("Iniciar como Cliente");
        btnLoginCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginCliente();
            }
        });

        JButton btnRegisterCliente = new JButton("Registrarse como Cliente");
        btnRegisterCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCliente();
            }
        });

        JButton btnLoginAdmin = new JButton("Iniciar como Administrador");
        btnLoginAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAdmin();
            }
        });

        panelPrincipal.add(btnLoginCliente);
        panelPrincipal.add(btnRegisterCliente);
        panelPrincipal.add(btnLoginAdmin);

        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
    }

    private void loginCliente() {
        String id = JOptionPane.showInputDialog(this, "Ingrese su DNI:");
        if (id == null) return; 

        clienteActual = catalogoClientes.buscarClientePorId(id);
        if (clienteActual != null) {
            clienteLoggedIn = true;
            adminLoggedIn = false; // Asegura que no quede el admin logueado por error
            JOptionPane.showMessageDialog(this, "Bienvenido, " + clienteActual.getNombre());
            mostrarMenuCliente();
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado. Por favor, regístrese.");
        }
    }


    private void registrarCliente() {
        String id, nombre, localidad, provincia, email;

        do {
            id = JOptionPane.showInputDialog(this, "Ingrese su DNI:");
            if (id == null) {
                return; //
            }
            if (!id.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Error, el DNI debe contener solo números.");
                id = "";
            } else if (catalogoClientes.buscarClientePorId(id) != null) {
                JOptionPane.showMessageDialog(this, "Error, el DNI ya existe. Por favor, elija otro DNI.");
                id = "";
            }
        } while (id.trim().isEmpty());

        String primerNombre = "";
        String apellidoPaterno = "";

        do {
            nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre y apellido:");
            if (nombre == null) {
                return; // 
            }
            String[] nombrePartes = nombre.trim().split(" ");
            if (nombrePartes.length < 2) {
                JOptionPane.showMessageDialog(this, "Error, reingrese su nombre y apellido. Debe ingresar al menos unprimer nombre y apellido.");
            } else {
                primerNombre = nombrePartes[0];
                apellidoPaterno = nombrePartes[1];
            }
        } while (primerNombre.isEmpty() || apellidoPaterno.isEmpty());

        String calle = "";
        String numero = "";
        String codigoPostal = "";

        do {
            calle = JOptionPane.showInputDialog(this, "Ingrese su calle:");
            if (calle == null) {
                return; 
            }
            if (calle.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error, reingrese su calle.");
            }
        } while (calle.trim().isEmpty());

        do {
            numero = JOptionPane.showInputDialog(this, "Ingrese su altura:");
            if (numero == null) {
                return;
            }
            if (!numero.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Error, la altura debe contener solo números.");
                numero = "";
            }
        } while (numero.trim().isEmpty());

        do {
            codigoPostal = JOptionPane.showInputDialog(this, "Ingrese su código postal:");
            if (codigoPostal == null) {
                return; // Se presionó "Cancelar"
            }
            if (!codigoPostal.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Error, el código postal debe contener solo números.");
                codigoPostal = "";
            }
        } while (codigoPostal.trim().isEmpty());

        String telefono = "";

        do {
            telefono = JOptionPane.showInputDialog(this, "Ingrese su teléfono (solo números):");
            if (telefono == null) {
                return;
            }
            if (!telefono.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Error, el teléfono debe contener solo números.");
                telefono = "";
            }
        } while (telefono.trim().isEmpty());

        do {
            localidad = JOptionPane.showInputDialog(this, "Ingrese su localidad:");
            if (localidad == null) {
                return; // Se presionó "Cancelar"
            }
            if (localidad.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error, reingrese su localidad.");
            }
        } while (localidad.trim().isEmpty());

        do {
            provincia = JOptionPane.showInputDialog(this, "Ingrese su provincia:");
            if (provincia == null) {
                return; // Se presionó "Cancelar"
            }
            if (provincia.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error, reingrese su provincia.");
            }
        } while (provincia.trim().isEmpty());

        String correo = "";
        String servidor = "";

        do {
            email = JOptionPane.showInputDialog(this, "Ingrese su e-mail (dirección@servidor):");
            if (email == null) {
                return;
            }
            String[] emailPartes = email.trim().split("@");
            if (emailPartes.length < 2) {
                JOptionPane.showMessageDialog(this, "Error, reingrese su e-mail. Debe contener la dirección, '@'y servidor.");
            } else {
                correo = emailPartes[0];
                servidor = emailPartes[1];
            }
        } while (correo.isEmpty() || servidor.isEmpty());

        Cliente clienteActual = new Cliente(id, primerNombre, apellidoPaterno, calle, numero, codigoPostal, "", telefono, localidad, provincia, correo, servidor);
        catalogoClientes.agregarCliente(clienteActual);
        try {
            catalogoClientes.guardarCatalogo(ARCHIVO_CLIENTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Registro exitoso. Bienvenido, " + clienteActual.getNombre());
        mostrarMenuCliente();
    }


    private void loginAdmin() {
        String password = JOptionPane.showInputDialog(this, "Ingrese la contraseña de administrador:");
        if (password == null) return;

        if ("admin".equals(password)) {
            adminLoggedIn = true;
            clienteLoggedIn = false; // para evitar que el cliente quede logueado
            JOptionPane.showMessageDialog(this, "Inicio de sesión como administrador exitoso.");
            mostrarMenuAdmin();
        } else {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
        }
    }
   

    private void mostrarMenuCliente() {
        JFrame frame = new JFrame("Menú Cliente");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 1));

        JButton btnRealizarPedido = new JButton("Realizar Pedido");
        btnRealizarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerPedido();
            }
        });

        JButton btnPagarPedido = new JButton("Pagar Pedido");
        btnPagarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarPedido();
            }
        });

        JButton btnVerCatalogo = new JButton("Ver Catálogo");
        btnVerCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCatalogo();
            }
        });

        JButton btnVentaDirecta = new JButton("Venta Directa");
        btnVentaDirecta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarVentaDirecta();
            }
        });
        
        
        JButton btnCancelarPedido = new JButton("Cancelar Pedido");
        btnCancelarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarPedido();
            }
        });
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                clienteLoggedIn = false;
            }
        });
        
        frame.add(btnRealizarPedido);
        frame.add(btnPagarPedido);
        frame.add(btnVerCatalogo);
        frame.add(btnVentaDirecta);
        frame.add(btnCancelarPedido);
        frame.setVisible(true);
    }

    private void mostrarMenuAdmin() {
        JFrame frame = new JFrame("Menú Administrador");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(8, 1));

        JButton btnAgregarAutoparte = new JButton("Agregar Autoparte");
        btnAgregarAutoparte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAutoparte();
            }
        });

        JButton btnModificarAutoparte = new JButton("Modificar Autoparte");
        btnModificarAutoparte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarAutoparte();
            }
        });

        JButton btnModificarStock = new JButton("Modificar Stock");
        btnModificarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarStock();
            }
        });
        
        
        JButton btnEliminarAutoparte = new JButton("Eliminar Autoparte");
        btnEliminarAutoparte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarAutoparte();
            }
        });

        JButton btnVerCatalogoAutopartes = new JButton("Catálogo de Autopartes");
        btnVerCatalogoAutopartes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCatalogoAutopartes();
            }
        });

        JButton btnVerListaClientes = new JButton("Listado de Clientes");
        btnVerListaClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verListaClientes();
            }
        });

        JButton btnVerPedidos = new JButton("Lista de pedidos pendientes");
        btnVerPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verPedidos();
            }
        });

        JButton btnVerVentas = new JButton("Registro de Ventas");
        btnVerVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verVentas();
            }
        });
        
        frame.add(btnAgregarAutoparte);
        frame.add(btnModificarAutoparte);
        frame.add(btnModificarStock);
        frame.add(btnEliminarAutoparte);
        frame.add(btnVerCatalogoAutopartes);
        frame.add(btnVerListaClientes);
        frame.add(btnVerPedidos);
        frame.add(btnVerVentas);
        frame.setVisible(true);
    }

    private void realizarVentaDirecta() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión como cliente para realizar una venta directa.");
            return;
        }

        List<Autoparte> autopartesSeleccionadas = new ArrayList<>();
        List<Autoparte> autopartes = catalogoAutopartes.listarAutopartes();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Map<Autoparte, JTextField> autopartesCantidad = new HashMap<>();

        for (Autoparte autoparte : autopartes) {
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

            String textoCheckBox = String.format("ID: %s, Nombre: %s, Marca: %s, Modelo: %s, Precio: $%.2f, Stock: %d",
                autoparte.getId(), autoparte.getDenominacion(), autoparte.getMarca(), autoparte.getModelo(), autoparte.getPrecio(), autoparte.getStock());

            JCheckBox checkBox = new JCheckBox(textoCheckBox);

            JTextField cantidadField = new JTextField("0", 5);
            cantidadField.setEnabled(false);

            checkBox.addActionListener(e -> cantidadField.setEnabled(checkBox.isSelected()));

            if (autoparte.getStock() == 0) {
                checkBox.setEnabled(false);
                checkBox.setText(textoCheckBox + " - Sin stock");
            }

            subPanel.add(checkBox);
            subPanel.add(new JLabel(" Cantidad: "));
            subPanel.add(cantidadField);

            panel.add(subPanel);
            autopartesCantidad.put(autoparte, cantidadField);
        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Seleccione las autopartes y cantidades", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            for (Map.Entry<Autoparte, JTextField> entry : autopartesCantidad.entrySet()) {
                Autoparte autoparte = entry.getKey();
                JTextField cantidadField = entry.getValue();
                if (cantidadField.isEnabled()) {
                    try {
                        int cantidad = Integer.parseInt(cantidadField.getText());
                        if (cantidad > 0 && cantidad <= autoparte.getStock()) {
                            for (int i = 0; i < cantidad; i++) {
                                autopartesSeleccionadas.add(autoparte);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Cantidad seleccionada para la autoparte ID: " + autoparte.getId() + " excede el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Cantidad inválida para la autoparte ID: " + autoparte.getId(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        if (autopartesSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se seleccionaron autopartes para la venta directa.");
            return;
        }

        // Calcular total de la venta
        double total = 0;
        Map<Autoparte, Integer> autopartesCantidadMap = new HashMap<>();
        for (Autoparte autoparte : autopartesSeleccionadas) {
            total += autoparte.getPrecio();
            autopartesCantidadMap.put(autoparte, autopartesCantidadMap.getOrDefault(autoparte, 0) + 1);
        }

        // Selección del método de pago
        String[] opcionesPago = {"Efectivo", "Débito", "Crédito"};
        String metodoPago = (String) JOptionPane.showInputDialog(null, "Seleccione el método de pago", "Método de Pago", JOptionPane.QUESTION_MESSAGE, null, opcionesPago, opcionesPago[0]);

        if (metodoPago != null) {
            Venta venta = null;
            Date fecha = new Date();
            String ventaId = "VD" + System.currentTimeMillis();

            switch (metodoPago) {
                case "Efectivo":
                    venta = new VentaEfectivo(ventaId, "", fecha, "Venta Directa", autopartesSeleccionadas.size(), total);
                    break;
                case "Débito":
                    venta = new VentaDebito(ventaId, "", fecha, "Venta Directa", autopartesSeleccionadas.size(), total);
                    break;
                case "Crédito":
                    String[] opcionesCuotas = {"2", "3", "6"};
                    String cuotasStr = (String) JOptionPane.showInputDialog(null, "Seleccione la cantidad de cuotas", "Cantidad de Cuotas", JOptionPane.QUESTION_MESSAGE, null, opcionesCuotas, opcionesCuotas[0]);
                    int cuotas = Integer.parseInt(cuotasStr);
                    venta = new VentaCredito(ventaId, "", fecha, "Venta Directa", autopartesSeleccionadas.size(), total, cuotas);
                    break;
            }

            if (venta != null) {
                venta.procesarPago();
                catalogoPedidos.agregarVenta(venta);
                try {
                    catalogoPedidos.guardarCatalogo(ARCHIVO_PEDIDOS);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar catálogo de pedidos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Actualizar stock de las autopartes seleccionadas
                for (Map.Entry<Autoparte, Integer> entry : autopartesCantidadMap.entrySet()) {
                    Autoparte autoparte = entry.getKey();
                    int cantidad = entry.getValue();
                    int nuevoStock = autoparte.getStock() - cantidad;
                    autoparte.setStock(nuevoStock);
                }

                try {
                    catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES); // Guardar catálogo de autopartes actualizado
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar catálogo de autopartes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Venta directa realizada con éxito.");
            }
        }
    }







    private void pagarVentaDirecta(DefaultTableModel tableModel) {
        double total = 0;
        List<DetallePedido> detalles = new ArrayList<>();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int cantidad = Integer.parseInt(tableModel.getValueAt(i, 3).toString());
            if (cantidad > 0) {
                String idAutoparte = tableModel.getValueAt(i, 0).toString();
                String nombreAutoparte = tableModel.getValueAt(i, 1).toString();
                double precioUnitario = Double.parseDouble(tableModel.getValueAt(i, 2).toString().replace(",", "."));
                double precioTotal = cantidad * precioUnitario;
                total += precioTotal;
                detalles.add(new DetallePedido(idAutoparte, "", cantidad, precioUnitario, precioTotal));
            }
        }

        String[] opcionesPago = {"Efectivo", "Débito", "Crédito"};
        String metodoPago = (String) JOptionPane.showInputDialog(null, "Seleccione el método de pago", "Método de Pago", JOptionPane.QUESTION_MESSAGE, null, opcionesPago, opcionesPago[0]);

        if (metodoPago != null) {
            Venta venta = null;
            Date fecha = new Date();
            String ventaId = "VD" + System.currentTimeMillis();
            
            switch (metodoPago) {
                case "Efectivo":
                    venta = new VentaEfectivo(ventaId, "", fecha, "Venta Directa", detalles.size(), total);
                    break;
                case "Débito":
                    venta = new VentaDebito(ventaId, "", fecha, "Venta Directa", detalles.size(), total);
                    break;
                case "Crédito":
                    int cuotas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de cuotas (2, 3 o 6):"));
                    venta = new VentaCredito(ventaId, "", fecha, "Venta Directa", detalles.size(), total, cuotas);
                    break;
            }

            if (venta != null) {
                venta.procesarPago();
                catalogoPedidos.agregarVenta(venta);
                try {
                    catalogoPedidos.guardarCatalogo(ARCHIVO_PEDIDOS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Venta directa realizada con éxito.");
            }
        }
    }
    
    private void modificarStock() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID de la autoparte para modificar el stock:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Autoparte autoparte = catalogoAutopartes.buscarAutoparte(id.trim());
        if (autoparte == null) {
            JOptionPane.showMessageDialog(this, "Autoparte no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoStockStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo valor de stock:");
        if (nuevoStockStr == null || nuevoStockStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Valor de stock no ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int nuevoStock = Integer.parseInt(nuevoStockStr.trim());
            if (nuevoStock < 0) {
                JOptionPane.showMessageDialog(this, "El valor del stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            autoparte.setStock(nuevoStock);
            catalogoAutopartes.actualizarAutoparte(autoparte);
            JOptionPane.showMessageDialog(this, "Stock modificado con éxito.");

            // Guardar el catálogo actualizado
            try {
                catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el catálogo.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        
    protected void agregarAutoparte() {
        JTextField idField = new JTextField();
        JTextField denominacionField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField categoriaField = new JTextField();
        JTextField marcaField = new JTextField();
        JTextField vehiculoField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField precioField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField stockMinimoField = new JTextField();
        JTextField enlaceField = new JTextField();

        Object[] message = {
            "ID:", idField,
            "Denominación:", denominacionField,
            "Descripción:", descripcionField,
            "Categoría:", categoriaField,
            "Marca:", marcaField,
            "Vehículo:", vehiculoField,
            "Modelo:", modeloField,
            "Precio:", precioField,
            "Stock:", stockField,
            "Stock Mínimo:", stockMinimoField,
            "Enlace:", enlaceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Autoparte", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String denominacion = denominacionField.getText();
                String descripcion = descripcionField.getText();
                String categoria = categoriaField.getText();
                String marca = marcaField.getText();
                String vehiculo = vehiculoField.getText();
                String modelo = modeloField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int stockMinimo = Integer.parseInt(stockMinimoField.getText());
                String enlace = enlaceField.getText();

                Autoparte autoparte = new Autoparte(id, denominacion, descripcion, categoria, marca, vehiculo, modelo, precio, stock, stockMinimo, enlace);
                catalogoAutopartes.agregarAutoparte(autoparte);
                catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES);

                JOptionPane.showMessageDialog(this, "Autoparte agregada con éxito.");
            } catch (NumberFormatException | IOException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar autoparte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    protected void modificarAutoparte() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID de la autoparte a modificar:");
        if (id != null && !id.trim().isEmpty()) {
            Autoparte autoparte = catalogoAutopartes.buscarAutoparte(id);
            if (autoparte != null) {
                JTextField denominacionField = new JTextField(autoparte.getDenominacion());
                JTextField descripcionField = new JTextField(autoparte.getDescripcion());
                JTextField categoriaField = new JTextField(autoparte.getCategoria());
                JTextField marcaField = new JTextField(autoparte.getMarca());
                JTextField vehiculoField = new JTextField(autoparte.getVehiculo());
                JTextField modeloField = new JTextField(autoparte.getModelo());
                JTextField precioField = new JTextField(String.valueOf(autoparte.getPrecio()));
                JTextField stockField = new JTextField(String.valueOf(autoparte.getStock()));
                JTextField stockMinimoField = new JTextField(String.valueOf(autoparte.getStockMinimo()));
                JTextField enlaceField = new JTextField(autoparte.getEnlace());

                Object[] message = {
                    "Denominación:", denominacionField,
                    "Descripción:", descripcionField,
                    "Categoría:", categoriaField,
                    "Marca:", marcaField,
                    "Vehículo:", vehiculoField,
                    "Modelo:", modeloField,
                    "Precio:", precioField,
                    "Stock:", stockField,
                    "Stock Mínimo:", stockMinimoField,
                    "Enlace:", enlaceField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Modificar Autoparte", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        autoparte.setDenominacion(denominacionField.getText());
                        autoparte.setDescripcion(descripcionField.getText());
                        autoparte.setCategoria(categoriaField.getText());
                        autoparte.setMarca(marcaField.getText());
                        autoparte.setVehiculo(vehiculoField.getText());
                        autoparte.setModelo(modeloField.getText());
                        autoparte.setPrecio(Double.parseDouble(precioField.getText()));
                        autoparte.setStock(Integer.parseInt(stockField.getText()));
                        autoparte.setStockMinimo(Integer.parseInt(stockMinimoField.getText()));
                        autoparte.setEnlace(enlaceField.getText());

                        catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES);

                        JOptionPane.showMessageDialog(this, "Autoparte modificada con éxito.");
                    } catch (NumberFormatException | IOException e) {
                        JOptionPane.showMessageDialog(this, "Error al modificar autoparte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Autoparte no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    protected void eliminarAutoparte() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID de la autoparte a eliminar:");
        if (id != null && !id.trim().isEmpty()) {
            Autoparte autoparte = catalogoAutopartes.buscarAutoparte(id);
            if (autoparte != null) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Está seguro de que desea eliminar la autoparte con ID: " + id + "?", 
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    catalogoAutopartes.eliminarAutoparte(id);
                    try {
                        catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES);
                        JOptionPane.showMessageDialog(this, "Autoparte eliminada con éxito.");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error al guardar catálogo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Autoparte no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verCatalogoAutopartes() {
        JFrame frame = new JFrame("Catálogo de Autopartes");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };

        model.addColumn("ID");
        model.addColumn("Denominación");
        model.addColumn("Descripción");
        model.addColumn("Categoría");
        model.addColumn("Marca");
        model.addColumn("Vehículo");
        model.addColumn("Modelo");
        model.addColumn("Precio");
        model.addColumn("Stock");
        model.addColumn("Stock Mínimo");
        model.addColumn("Enlace");

        for (Autoparte autoparte : catalogoAutopartes.listarAutopartes()) {
            model.addRow(new Object[]{
                autoparte.getId(),
                autoparte.getDenominacion(),
                autoparte.getDescripcion(),
                autoparte.getCategoria(),
                autoparte.getMarca(),
                autoparte.getVehiculo(),
                autoparte.getModelo(),
                autoparte.getPrecio(),
                autoparte.getStock(),
                autoparte.getStockMinimo(),
                autoparte.getEnlace()
            });
        }

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                int stock = (int) getValueAt(row, 8);
                int stockMinimo = (int) getValueAt(row, 9);
                if (stock <= stockMinimo) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
                if (stock == 0) {
                    c.setForeground(Color.GRAY);
                }
                return c;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    private void cancelarPedido() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión como cliente para cancelar un pedido.");
            return;
        }

        List<Pedido> pedidosCliente = catalogoPedidos.getPedidosPorCliente(clienteActual.getId());

        if (pedidosCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pedidos para cancelar.");
            return;
        }

        String[] pedidosArray = new String[pedidosCliente.size()];
        for (int i = 0; i < pedidosCliente.size(); i++) {
            pedidosArray[i] = pedidosCliente.get(i).toString();
        }

        String pedidoSeleccionado = (String) JOptionPane.showInputDialog(this, "Seleccione el pedido a cancelar", "Cancelar Pedido", JOptionPane.QUESTION_MESSAGE, null, pedidosArray, pedidosArray[0]);

        if (pedidoSeleccionado != null) {
            Pedido pedidoACancelar = null;
            for (Pedido pedido : pedidosCliente) {
                if (pedido.toString().equals(pedidoSeleccionado)) {
                    pedidoACancelar = pedido;
                    break;
                }
            }

            if (pedidoACancelar != null) {
                // Devolver el stock de las autopartes al canclar pedido
                for (DetallePedido detalle : pedidoACancelar.getDetalles()) {
                    Autoparte autoparte = catalogoAutopartes.buscarAutoparte(detalle.getIdAutoparte());
                    if (autoparte != null) {
                        autoparte.setStock(autoparte.getStock() + detalle.getCantidad());
                        catalogoAutopartes.actualizarAutoparte(autoparte); 
                    }
                }

                catalogoPedidos.eliminarPedido(pedidoACancelar.getId()); // Eliminar el pedido del catálogo de pedidos
                try {
                    catalogoPedidos.guardarCatalogo(ARCHIVO_PEDIDOS); // Guarda cambios en el catálogo de pedidos
                    catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES); //Guarda cambios en el catálogo de autopartes
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar los cambios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Pedido cancelado con éxito.");
            }
        }
    }








private void verListaClientes() {
    JFrame frame = new JFrame("Lista de Clientes");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Nombre");
    model.addColumn("Dirección");
    model.addColumn("Teléfono");
    model.addColumn("Localidad");
    model.addColumn("Provincia");
    model.addColumn("Email");

    for (Cliente cliente : catalogoClientes.listarClientes()) {
        model.addRow(new Object[]{
            cliente.getId(), 
            cliente.getNombre(), 
            cliente.getDireccion(), 
            cliente.getTelefono(), 
            cliente.getLocalidad(), 
            cliente.getProvincia(), 
            cliente.getEmail()
        });
    }

    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.setVisible(true);
}

    private void verPedidos() {
        JFrame frame = new JFrame("Lista de Pedidos");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pedido");
        model.addColumn("ID Cliente");
        model.addColumn("Fecha");
        model.addColumn("Total");

        for (Pedido pedido : catalogoPedidos.getPedidos()) {
            model.addRow(new Object[]{pedido.getId(), pedido.getClienteId(), pedido.getFecha().toString(), pedido.getTotal()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    private void verVentas() {
        JFrame frame = new JFrame("Lista de Ventas");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Venta");
        model.addColumn("ID Pedido");
        model.addColumn("Fecha");
        model.addColumn("Total");

        DecimalFormat df = new DecimalFormat("#.00"); // mostrar solo dos decimales

        for (Venta venta : catalogoPedidos.getVentas()) { 
            String totalFormatted = df.format(venta.getMontoTotal());
            model.addRow(new Object[]{venta.getId(), venta.getPedidoId(), venta.getFecha().toString(), totalFormatted});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    
    
    private void hacerPedido() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión como cliente para realizar un pedido.");
            return;
        }

        List<Autoparte> autopartesSeleccionadas = new ArrayList<>();
        List<Autoparte> autopartes = catalogoAutopartes.listarAutopartes();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Map<Autoparte, JTextField> autopartesCantidad = new HashMap<>();

        for (Autoparte autoparte : autopartes) {
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

            String textoCheckBox = String.format("ID: %s, Nombre: %s, Marca: %s, Modelo: %s, Precio: $%.2f, Stock: %d",
                autoparte.getId(), autoparte.getDenominacion(), autoparte.getMarca(), autoparte.getModelo(), autoparte.getPrecio(), autoparte.getStock());

            JCheckBox checkBox = new JCheckBox(textoCheckBox);

            JTextField cantidadField = new JTextField("0", 5);
            cantidadField.setEnabled(false);

            checkBox.addActionListener(e -> cantidadField.setEnabled(checkBox.isSelected()));

            if (autoparte.getStock() == 0) {
                checkBox.setEnabled(false);
                checkBox.setText(textoCheckBox + " - Sin stock");
            }

            subPanel.add(checkBox);
            subPanel.add(new JLabel(" Cantidad: "));
            subPanel.add(cantidadField);

            panel.add(subPanel);
            autopartesCantidad.put(autoparte, cantidadField);
        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Seleccione las autopartes y cantidades", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<DetallePedido> detalles = new ArrayList<>(); 

            for (Map.Entry<Autoparte, JTextField> entry : autopartesCantidad.entrySet()) {
                Autoparte autoparte = entry.getKey();
                JTextField cantidadField = entry.getValue();
                if (cantidadField.isEnabled()) {
                    try {
                        int cantidad = Integer.parseInt(cantidadField.getText());
                        if (cantidad > 0 && cantidad <= autoparte.getStock()) {
                            detalles.add(new DetallePedido(autoparte.getId(), "", cantidad, autoparte.getPrecio(), cantidad * autoparte.getPrecio()));
                            autopartesSeleccionadas.add(autoparte);
                        } else {
                            JOptionPane.showMessageDialog(this, "Cantidad seleccionada para la autoparte ID: " + autoparte.getId() + " excede el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Cantidad inválida para la autoparte ID: " + autoparte.getId(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            if (autopartesSeleccionadas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se seleccionaron autopartes para el pedido.");
                return;
            }

            // Generador de ID para el nuevo pedido
            String nuevoIdPedido = "PED" + (catalogoPedidos.getPedidos().size() + 1);
            double totalPedido = detalles.stream().mapToDouble(DetallePedido::getPrecioTotal).sum();

            Pedido nuevoPedido = new Pedido(nuevoIdPedido, clienteActual.getId(), new Date(), totalPedido, detalles);
            catalogoPedidos.agregarPedido(nuevoPedido);

            try {
                catalogoPedidos.guardarCatalogo(ARCHIVO_PEDIDOS);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar catálogo de pedidos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            for (DetallePedido detalle : detalles) {
                Autoparte autoparte = catalogoAutopartes.buscarAutoparte(detalle.getIdAutoparte());
                if (autoparte != null) {
                    autoparte.setStock(autoparte.getStock() - detalle.getCantidad());
                }
            }

            try {
                catalogoAutopartes.guardarCatalogo(ARCHIVO_AUTOPARTES);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar catálogo de autopartes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Pedido realizado con éxito.");
        }
    }



    private void pagarPedido() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(null, "Debe iniciar sesión como cliente para pagar un pedido.");
            return;
        }

        List<Pedido> pedidosCliente = catalogoPedidos.buscarPedidosPorClienteId(clienteActual.getId());

        if (pedidosCliente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tiene pedidos pendientes.");
            return;
        }

        String[] opcionesPedidos = new String[pedidosCliente.size()];
        for (int i = 0; i < pedidosCliente.size(); i++) {
            opcionesPedidos[i] = "Pedido ID: " + pedidosCliente.get(i).getId() + " - Total: $" + DECIMAL_FORMAT.format(pedidosCliente.get(i).getTotal());
        }

        String pedidoSeleccionadoStr = (String) JOptionPane.showInputDialog(null, "Seleccione un pedido para pagar:",
                "Pagar Pedido", JOptionPane.QUESTION_MESSAGE, null, opcionesPedidos, opcionesPedidos[0]);

        if (pedidoSeleccionadoStr == null) return; 

        int indicePedidoSeleccionado = -1;
        for (int i = 0; i < opcionesPedidos.length; i++) {
            if (opcionesPedidos[i].equals(pedidoSeleccionadoStr)) {
                indicePedidoSeleccionado = i;
                break;
            }
        }

        if (indicePedidoSeleccionado == -1) return; 

        Pedido pedidoSeleccionado = pedidosCliente.get(indicePedidoSeleccionado);
        double basePrice = pedidoSeleccionado.getTotal();

        JFrame frame = new JFrame("Método de Pago");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        JLabel labelTotal = new JLabel("Total de la compra: $" + DECIMAL_FORMAT.format(basePrice));
        JLabel labelFinalPrice = new JLabel("Precio final: $" + DECIMAL_FORMAT.format(basePrice));
        panel.add(labelTotal);
        panel.add(labelFinalPrice);

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Efectivo", "Tarjeta de crédito", "Tarjeta de débito"});
        panel.add(comboBox);

        JComboBox<String> cuotasComboBox = new JComboBox<>(new String[]{"2 cuotas", "3 cuotas", "6 cuotas"});
        cuotasComboBox.setVisible(false); 
        panel.add(cuotasComboBox);

        ActionListener actualizarPrecioFinal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPaymentMethod = (String) comboBox.getSelectedItem();
                String selectedCuota = (String) cuotasComboBox.getSelectedItem();
                double finalPrice = basePrice;

                if ("Efectivo".equals(selectedPaymentMethod)) {
                    finalPrice *= 0.9;
                    cuotasComboBox.setVisible(false);
                } else if ("Tarjeta de débito".equals(selectedPaymentMethod)) {
                    finalPrice *= 0.95;
                    cuotasComboBox.setVisible(false);
                } else if ("Tarjeta de crédito".equals(selectedPaymentMethod)) {
                    cuotasComboBox.setVisible(true); // Mostrar opciones de cuotas
                    if (selectedCuota != null) {
                        switch (selectedCuota) {
                            case "2 cuotas":
                                finalPrice *= 1.06;
                                break;
                            case "3 cuotas":
                                finalPrice *= 1.12;
                                break;
                            case "6 cuotas":
                                finalPrice *= 1.20;
                                break;
                        }
                    }
                }

                labelFinalPrice.setText("Precio final: $" + DECIMAL_FORMAT.format(finalPrice));
                frame.pack(); // Ajustar el tamaño del frame para acomodar los componentes visibles
            }
        };

        comboBox.addActionListener(actualizarPrecioFinal);
        cuotasComboBox.addActionListener(actualizarPrecioFinal);

        JButton botonConfirmar = new JButton("Confirmar Pago");
        botonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPaymentMethod = (String) comboBox.getSelectedItem();
                String selectedCuota = (String) cuotasComboBox.getSelectedItem();
                if (selectedPaymentMethod == null) {
                    JOptionPane.showMessageDialog(frame, "Seleccione un método de pago.");
                    return;
                }

                double finalPrice = basePrice;
                Venta nuevaVenta;

                if ("Efectivo".equals(selectedPaymentMethod)) {
                    finalPrice *= 0.9;
                    nuevaVenta = new VentaEfectivo(
                            generateVentaId(),
                            pedidoSeleccionado.getId(),
                            new Date(),
                            "Descripción del pedido", 
                            1, // Cantidad
                            finalPrice
                    );
                } else if ("Tarjeta de débito".equals(selectedPaymentMethod)) {
                    finalPrice *= 0.95;
                    nuevaVenta = new VentaDebito(
                            generateVentaId(),
                            pedidoSeleccionado.getId(),
                            new Date(),
                            "Descripción del pedido", 
                            1, // Cantidad
                            finalPrice
                    );
                } else {
                    int cuotas = 0;
                    if (selectedCuota != null) {
                        switch (selectedCuota) {
                            case "2 cuotas":
                                finalPrice *= 1.06;
                                cuotas = 2;
                                break;
                            case "3 cuotas":
                                finalPrice *= 1.12;
                                cuotas = 3;
                                break;
                            case "6 cuotas":
                                finalPrice *= 1.20;
                                cuotas = 6;
                                break;
                        }
                    }
                    nuevaVenta = new VentaCredito(
                            generateVentaId(),
                            pedidoSeleccionado.getId(),
                            new Date(),
                            "Descripción del pedido",
                            1, // Cantidad
                            finalPrice,
                            cuotas
                    );
                }

              
                catalogoPedidos.agregarVenta(nuevaVenta);

                catalogoPedidos.eliminarPedido(pedidoSeleccionado.getId());

                try {
                    catalogoPedidos.guardarCatalogo("catalogo_pedidos.dat");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(frame, "Pago confirmado. Precio final: $" + DECIMAL_FORMAT.format(finalPrice));
                frame.dispose();             }
        });

        panel.add(botonConfirmar);
        frame.setVisible(true);
    }



    private String generateVentaId() {
        return "VENTA" + System.currentTimeMillis();
    }


    private void verCatalogo() {
        verCatalogoAutopartes();
    }
    
    
    
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
                main.setVisible(true);
            }
        });
    }
}

