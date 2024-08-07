package tp;

import java.io.Serializable;

public class Autoparte implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String denominacion;
    private String descripcion;
    private String categoria;
    private String marca;
    private String vehiculo;
    private String modelo;
    private double precio;
    private int stock;
    private int stockMinimo;
    private String enlace;

    public Autoparte(String id, String denominacion, String descripcion, String categoria, String marca, String vehiculo, String modelo, double precio, int stock, int stockMinimo, String enlace) {
        this.id = id;
        this.denominacion = denominacion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.marca = marca;
        this.vehiculo = vehiculo;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.enlace = enlace;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    @Override
    public String toString() {
        return "Autoparte{" +
                "id='" + id + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", marca='" + marca + '\'' +
                ", vehiculo='" + vehiculo + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                ", enlace='" + enlace + '\'' +
                '}';
    }
}

