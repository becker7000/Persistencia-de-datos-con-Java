package mx.ceined.app.models;

import java.util.Date;

public class Producto {

    private Integer id;
    private String nombre;
    private Double precio;
    private Date fechaRegistro;

    public Producto() {
    }

    public Producto(Integer id, String nombre, Double precio, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void mostrar(){
        System.out.printf("\n\t | %d %s $%.2f %s",id,nombre,precio,fechaRegistro);
    }

}
