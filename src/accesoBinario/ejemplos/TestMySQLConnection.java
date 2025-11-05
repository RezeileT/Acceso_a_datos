package accesoBinario.ejemplos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String usuario = "root";
        String password = "mysql";
        try (Connection conn = DriverManager.getConnection(url, usuario, password)){
            System.out.println("Conección exitosa");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
