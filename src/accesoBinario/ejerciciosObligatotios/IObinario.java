package accesoBinario.ejerciciosObligatotios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Precio: " + precio + ", Stock: " + stock;
    }
}

public class IObinario {

    public static void escribirProducto(String archivo, Producto producto) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            dos.writeInt(producto.getId());
            dos.writeUTF(producto.getNombre());
            dos.writeDouble(producto.getPrecio());
            dos.writeInt(producto.getStock());
            dos.flush();
        }
    }

    public static void agregarProducto(String archivo, Producto producto) throws IOException{
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo, true))) {
            dos.writeInt(producto.getId());
            dos.writeUTF(producto.getNombre());
            dos.writeDouble(producto.getPrecio());
            dos.writeInt(producto.getStock());
            dos.flush();
        }
    }

    public static List<Producto> leerProductos(String archivo) throws IOException{

        List<Producto> productos = new ArrayList<>();
        try(DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            boolean fin = true;
            while(fin) {
                try {
                    int id = dis.readInt();
                    String nombre = dis.readUTF();
                    double precio = dis.readDouble();
                    int stock = dis.readInt();
                    productos.add(new Producto(id, nombre, precio, stock));
                }catch(EOFException e) {
                    fin = false;
                }
            }
        }
        return productos;
    }

    static void main(String[] args) {
        Producto p1 = new Producto(1, "Laptop", 999.99, 10);
        Producto p2 = new Producto(2, "Mouse", 19.99, 50);



        try {
            escribirProducto("src/accesoBinario/resources/inventario.dat", p1);
            agregarProducto("src/accesoBinario/resources/inventario.dat", p2);

        }catch (IOException e){
            System.err.println("Error" + e.getMessage());
        }

        try{
            List<Producto> productos = leerProductos("src/accesoBinario/resources/inventario.dat");
            for (Producto producto : productos) {
                System.out.println(producto);
            }
        }catch (IOException e){
            System.err.println("Error" + e.getMessage());
        }
    }
}
