package accesoBinario.ejerciciosOpcionales;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCBinario {

    private static Statement sentencia;
    private static ResultSet resultado;
    private static Connection conexion = null;
    private static List<Producto> productos = new ArrayList<>();

    static class Producto {
        int id;
        String nombre;

        public Producto(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
    }

    public static int exportarProductos(Connection conn, String archivo) throws SQLException, IOException {
        productos = new ArrayList<>();
        sentencia = conn.createStatement();
        String sql = "SELECT id, nombre FROM productos";
        resultado = sentencia.executeQuery(sql);
        int filasObtenidas = 0;

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String nombre = resultado.getString("nombre");
            Producto producto = new Producto(id,nombre);
            productos.add(producto);
            System.out.println("ID: " + id + ", Nombre: " + nombre);
        }

        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))){
        for (Producto producto : productos) {
            filasObtenidas++;
            dos.writeInt(producto.id);
            dos.writeUTF(producto.nombre);
        }
        }

        return filasObtenidas;
    }

    public static int importarProductos(Connection conn, String archivo) throws SQLException, IOException {
        List<Producto> productos = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (true) {
                try {
                    int id = dis.readInt();
                    String nombre = dis.readUTF();
                    productos.add(new Producto(id, nombre));
                } catch (EOFException e) {
                    break;
                }
            }
        }

        String sql = "INSERT INTO productos (id, nombre) VALUES (?, ?)";
        int filasInsertadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Producto producto : productos) {
                pstmt.setInt(1, producto.id);
                pstmt.setString(2, producto.nombre);
                filasInsertadas += pstmt.executeUpdate();
            }
        }

        return filasInsertadas;
    }


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String usuario = "root";
        String password = "mysql";
        String ruta = "src/accesoBinario/resources";
        String archivo = "backup_productos.dat";

        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            int exportados = exportarProductos(conexion, ruta + File.separator + archivo);
            System.out.println("Productos exportados: " + exportados);
            sentencia = conexion.createStatement();
            //sentencia.executeUpdate("DELETE FROM productos WHERE id=1");
            int importados = importarProductos(conexion, ruta + File.separator + archivo);
            System.out.println("Productos importados: " + importados);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
