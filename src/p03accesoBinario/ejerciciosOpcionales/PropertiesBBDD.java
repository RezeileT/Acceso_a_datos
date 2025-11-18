package p03accesoBinario.ejerciciosOpcionales;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PropertiesBBDD {
    public static int migrarPropertiesABD(String archivo, Connection conn) throws IOException,SQLException {
        return 0;
    }
    public static int exportarBDaProperties(Connection conn, String archivo) throws SQLException, IOException {
        return 0;
    }
    public static int sincronizarPropiedades(String archivo, Connection conn) throws IOException, SQLException {
        return 0;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/config_db";
        String usuario = "root";
        String password = "mysql";
        String ruta = "src/p03accesoBinario/resources";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, password);
            // Migrar de archivo a BD
            int migradas = migrarPropertiesABD(ruta + File.separator + "config.properties", conn);
            System.out.println("Propiedades migradas a BD: " + migradas);
            // Modificar en BD
            PreparedStatement pstmt = conn.prepareStatement("UPDATE configuracion SET valor = ? WHERE clave = ?");
            pstmt.setString(1, "3307");
            pstmt.setString(2, "db.port");
            pstmt.executeUpdate();
            // Exportar de BD a archivo
            int exportadas = exportarBDaProperties(conn, "config_exportado.properties");
            System.out.println("Propiedades exportadas a archivo: " + exportadas);
            conn.close();
        }catch (SQLException sqlException){
            System.err.println("Error con la base de datos: " + sqlException.getMessage());
        }catch (IOException ioException){
            System.err.println("Error al acceder al archivo: " + ioException.getMessage());
        }
    }
}
