package tp;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String localidad;
    private String provincia;
    private String email;

    public Cliente(String id, String primerNombre, String apellidoPaterno, String calle, String numero, String codigoPostal, String caracteristica, String numeroTelefono, String localidad, String provincia, String correo, String servidor) {
        if (!id.matches("\\d+")) {
            throw new IllegalArgumentException("El ID debe contener solo números.");
        }
        this.id = id;
        this.nombre = primerNombre + " " + apellidoPaterno;
        this.direccion = calle + " " + numero + " (" + codigoPostal + ")";
        this.telefono = caracteristica + "-" + numeroTelefono;
        this.localidad = localidad;
        this.provincia = provincia;
        this.email = correo + "@" + servidor;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getEmail() {
        return email;
    }
}
