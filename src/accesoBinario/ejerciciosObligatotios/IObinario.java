package accesoBinario.ejerciciosObligatotios;

import java.io.IOException;

class Producto{
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}

public class IObinario {

    public static void escribirProducto(String archivo, Producto producto) throws IOException {

    }

    public static void agregarProducto(String archivo, Producto producto) throws IOException{

    }

    static void main(String[] args) {
        Producto p1 = new Producto(1, "Laptop", 999.99, 10);
        Producto p2 = new Producto(2, "Mouse", 19.99, 50);
        try {
            escribirProducto("inventario.dat", p1);
            agregarProducto("inventario.dat", p2);
        }catch (IOException e){
            System.err.println("Error" + e.getMessage());
        }
    }
}
