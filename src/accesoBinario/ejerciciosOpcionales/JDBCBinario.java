package accesoBinario.ejerciciosOpcionales;

import java.io.IOException;
import java.sql.*;

public class JDBCBinario {

    private static Statement sentencia;
    private static ResultSet resultado;
    private static Connection conexion = null;

    public static int exportarProductos(Connection conn, String archivo) throws SQLException, IOException {
        return 0;
    }
    public static int importarProductos(Connection conn, String archivo) throws SQLException, IOException{
        return 0;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String usuario = "root";
        String password = "mysql";

        try (Connection conn = DriverManager.getConnection(url, usuario, password)){
            int exportados = exportarProductos(conn, "backup_productos.dat");
            System.out.println("Productos exportados: " + exportados);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM productos");
            int importados = importarProductos(conn, "backup_productos.dat");
            System.out.println("Productos importados: " + importados);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
