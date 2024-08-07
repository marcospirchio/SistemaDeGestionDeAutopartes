package tp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarAutoparte extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtid;
    private JTextField txtDenominacion;
    private JTextField txtDescripcion;
    private JTextField txtCategoria;
    private JTextField txtMarca;
    private JTextField txtVehiculo;
    private JTextField txtModelo;
    private JTextField txtPrecio;
    private JTextField txtEnlace;
    private JTextField txtStock;
    private JTextField txtStockMinimo;
    private CatalogoAutopartes catalogo;

    public AgregarAutoparte(CatalogoAutopartes catalogo) {
        this.catalogo = catalogo;
        setTitle("Agregar Autoparte");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblid = new JLabel("ID:");
        lblid.setBounds(10, 10, 80, 25);
        getContentPane().add(lblid);

        txtid = new JTextField();
        txtid.setBounds(100, 10, 160, 25);
        getContentPane().add(txtid);

        JLabel lblDenominacion = new JLabel("Denominación:");
        lblDenominacion.setBounds(10, 45, 80, 25);
        getContentPane().add(lblDenominacion);

        txtDenominacion = new JTextField();
        txtDenominacion.setBounds(100, 45, 160, 25);
        getContentPane().add(txtDenominacion);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(10, 80, 80, 25);
        getContentPane().add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(100, 80, 160, 25);
        getContentPane().add(txtDescripcion);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(10, 115, 80, 25);
        getContentPane().add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 115, 160, 25);
        getContentPane().add(txtCategoria);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(10, 150, 80, 25);
        getContentPane().add(lblMarca);

        txtMarca = new JTextField();
        txtMarca.setBounds(100, 150, 160, 25);
        getContentPane().add(txtMarca);

        JLabel lblVehiculo = new JLabel("Vehículo:");
        lblVehiculo.setBounds(10, 185, 80, 25);
        getContentPane().add(lblVehiculo);

        txtVehiculo = new JTextField();
        txtVehiculo.setBounds(100, 185, 160, 25);
        getContentPane().add(txtVehiculo);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(10, 220, 80, 25);
        getContentPane().add(lblModelo);

        txtModelo = new JTextField();
        txtModelo.setBounds(100, 220, 160, 25);
        getContentPane().add(txtModelo);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 255, 80, 25);
        getContentPane().add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(100, 255, 160, 25);
        getContentPane().add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(10, 290, 80, 25);
        getContentPane().add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(100, 290, 160, 25);
        getContentPane().add(txtStock);

        JLabel lblStockMinimo = new JLabel("Stock Mínimo:");
        lblStockMinimo.setBounds(10, 325, 80, 25);
        getContentPane().add(lblStockMinimo);

        txtStockMinimo = new JTextField();
        txtStockMinimo.setBounds(100, 325, 160, 25);
        getContentPane().add(txtStockMinimo);

        JLabel lblEnlace = new JLabel("Enlace:");
        lblEnlace.setBounds(10, 360, 80, 25);
        getContentPane().add(lblEnlace);

        txtEnlace = new JTextField();
        txtEnlace.setBounds(100, 360, 160, 25);
        getContentPane().add(txtEnlace);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 395, 160, 25);
        getContentPane().add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtid.getText();
                    String denominacion = txtDenominacion.getText();
                    String descripcion = txtDescripcion.getText();
                    String categoria = txtCategoria.getText();
                    String marca = txtMarca.getText();
                    String vehiculo = txtVehiculo.getText();
                    String modelo = txtModelo.getText();
                    double precio = Double.parseDouble(txtPrecio.getText());
                    int stock = Integer.parseInt(txtStock.getText());
                    int stockMinimo = Integer.parseInt(txtStockMinimo.getText());
                    String enlace = txtEnlace.getText();

                    Autoparte autoparte = new Autoparte(id, denominacion, descripcion, categoria, marca, vehiculo, modelo, precio, stock, stockMinimo, enlace);
                    catalogo.agregarAutoparte(autoparte);
                    JOptionPane.showMessageDialog(null, "Autoparte agregada exitosamente");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en los datos ingresados: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar la autoparte: " + ex.getMessage());
                }
            }
        });
    }
}


