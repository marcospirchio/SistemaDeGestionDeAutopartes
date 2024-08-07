package tp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarAutoparte extends JDialog {

    private static final long serialVersionUID = 1L;
    private CatalogoAutopartes catalogo;
    private Autoparte autoparte;

    private JTextField txtId;
    private JTextField txtDenominacion;
    private JTextField txtDescripcion;
    private JTextField txtCategoria;
    private JTextField txtMarca;
    private JTextField txtVehiculo;
    private JTextField txtModelo;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JTextField txtStockMinimo;
    private JTextField txtEnlace;

    public ModificarAutoparte(Frame owner, CatalogoAutopartes catalogo, Autoparte autoparte) {
        super(owner, true);
        this.catalogo = catalogo;
        this.autoparte = autoparte;

        setTitle("Modificar Autoparte");
        setSize(400, 400);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(12, 2));

        add(new JLabel("ID:"));
        txtId = new JTextField(autoparte.getId());
        txtId.setEditable(false);
        add(txtId);

        add(new JLabel("Denominación:"));
        txtDenominacion = new JTextField(autoparte.getDenominacion());
        add(txtDenominacion);

        add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField(autoparte.getDescripcion());
        add(txtDescripcion);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField(autoparte.getCategoria());
        add(txtCategoria);

        add(new JLabel("Marca:"));
        txtMarca = new JTextField(autoparte.getMarca());
        add(txtMarca);

        add(new JLabel("Vehículo:"));
        txtVehiculo = new JTextField(autoparte.getVehiculo());
        add(txtVehiculo);

        add(new JLabel("Modelo:"));
        txtModelo = new JTextField(autoparte.getModelo());
        add(txtModelo);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField(String.valueOf(autoparte.getPrecio()));
        add(txtPrecio);

        add(new JLabel("Stock:"));
        txtStock = new JTextField(String.valueOf(autoparte.getStock()));
        add(txtStock);

        add(new JLabel("Stock Mínimo:"));
        txtStockMinimo = new JTextField(String.valueOf(autoparte.getStockMinimo()));
        add(txtStockMinimo);

        add(new JLabel("Enlace:"));
        txtEnlace = new JTextField(autoparte.getEnlace());
        add(txtEnlace);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarAutoparte();
            }
        });
        add(btnModificar);

        JButton btnModificarStock = new JButton("Modificar Stock");
        btnModificarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarStock();
            }
        });
        add(btnModificarStock);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btnCancelar);
    }

    private void modificarAutoparte() {
        try {
            String denominacion = txtDenominacion.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String marca = txtMarca.getText().trim();
            String vehiculo = txtVehiculo.getText().trim();
            String modelo = txtModelo.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            int stockMinimo = Integer.parseInt(txtStockMinimo.getText().trim());
            String enlace = txtEnlace.getText().trim();

            if (denominacion.isEmpty() || descripcion.isEmpty() || categoria.isEmpty() || marca.isEmpty() || vehiculo.isEmpty() || modelo.isEmpty() || enlace.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (precio < 0 || stock < 0 || stockMinimo < 0) {
                JOptionPane.showMessageDialog(this, "Los valores no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            autoparte.setDenominacion(denominacion);
            autoparte.setDescripcion(descripcion);
            autoparte.setCategoria(categoria);
            autoparte.setMarca(marca);
            autoparte.setVehiculo(vehiculo);
            autoparte.setModelo(modelo);
            autoparte.setPrecio(precio);
            autoparte.setStock(stock);
            autoparte.setStockMinimo(stockMinimo);
            autoparte.setEnlace(enlace);

            catalogo.actualizarAutoparte(autoparte);

            JOptionPane.showMessageDialog(this, "Autoparte modificada con éxito.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en los valores numericos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarStock() {
        try {
            String id = JOptionPane.showInputDialog(this, "Ingrese el ID de la autoparte:", "Modificar Stock", JOptionPane.PLAIN_MESSAGE);
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID no ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Autoparte autoparte = catalogo.buscarAutoparte(id.trim());
            if (autoparte == null) {
                JOptionPane.showMessageDialog(this, "Autoparte no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nuevoStockStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo valor de stock:", "Modificar Stock", JOptionPane.PLAIN_MESSAGE);
            if (nuevoStockStr == null || nuevoStockStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Valor de stock no ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nuevoStock = Integer.parseInt(nuevoStockStr.trim());

            if (nuevoStock < 0) {
                JOptionPane.showMessageDialog(this, "El valor del stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            autoparte.setStock(nuevoStock);
            catalogo.actualizarAutoparte(autoparte);

            JOptionPane.showMessageDialog(this, "Stock modificado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

