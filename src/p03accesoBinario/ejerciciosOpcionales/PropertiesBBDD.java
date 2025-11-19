package p03accesoBinario.ejerciciosOpcionales;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class PropertiesBBDD {
    //Sentencias:
    private static final String INSERT = "INSERT INTO configuracion (nombre, valor) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE configuracion SET valor = ? WHERE nombre = ?";
    private static final String SELECT = "SELECT nombre, valor FROM configuracion";

    public static void crearTabla(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS configuracion (" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                "nombre varchar(100) NOT NULL," +
                "valor varchar(100) NOT NULL)");
        System.out.println("Tabla configuracion creada");
    }

    public static int migrarPropertiesABD(String archivo, Connection conn) throws IOException,SQLException {
        int numeroPropiedades = 0;
        Properties config = new Properties();
        config.load(new FileInputStream(archivo));
        for (String key : config.stringPropertyNames()) {
            String value = config.getProperty(key);
            System.out.println(key + " = " + value);

            PreparedStatement ps = conn.prepareStatement(INSERT);
            ps.setString(1, key);
            ps.setString(2, config.getProperty(key));
            numeroPropiedades += ps.executeUpdate();
        }

        return numeroPropiedades;
    }
    public static int exportarBDaProperties(Connection conn, String archivo) throws SQLException, IOException {
        int numeroPropiedades = 0;
        ResultSet resultado = conn.createStatement().executeQuery(SELECT);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            while (resultado.next()) {
                String nombre = resultado.getString(1);
                String valor = resultado.getString(2);
                bw.write(nombre + "=" + valor);
                bw.newLine();
                numeroPropiedades++;
            }
            resultado.close();
        }

        return numeroPropiedades;
    }
    public static int sincronizarPropiedades(String archivo, Connection conn) throws IOException, SQLException {
        int numeroPropiedades = 0;
        Properties config = new Properties();
        config.load(new FileInputStream(archivo));
        ResultSet resultado = conn.createStatement().executeQuery(SELECT);
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            for (String key : config.stringPropertyNames()) {
                while (resultado.next()) {
                    String nombre = resultado.getString(1);
                    String valor = resultado.getString(2);
                    if (key.equals(nombre) && !valor.equals(config.getProperty(key))) {
                        PreparedStatement ps = conn.prepareStatement(UPDATE);
                        ps.setString(1, config.getProperty(key));
                        ps.setString(2, key);
                        numeroPropiedades += ps.executeUpdate();
                    }
                }
            }
            resultado.close();
        }

        return numeroPropiedades;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String usuario = "root";
        String password = "mysql";
        String ruta = "src/p03accesoBinario/resources";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, password);
            crearTabla(conn);
            // Migrar de archivo a BD
            int migradas = migrarPropertiesABD(ruta + File.separator + "config.properties", conn);
            System.out.println("Propiedades migradas a BD: " + migradas);
            // Modificar en BD
            PreparedStatement pstmt = conn.prepareStatement("UPDATE configuracion SET valor = ? WHERE nombre = ?");
            pstmt.setString(1, "3307");
            pstmt.setString(2, "db.port");
            pstmt.executeUpdate();
            // Exportar de BD a archivo
            int exportadas = exportarBDaProperties(conn, ruta + File.separator + "config_exportado.properties");
            System.out.println("Propiedades exportadas a archivo: " + exportadas);
            int sincronizadas = sincronizarPropiedades(ruta + File.separator + "config_exportado.properties", conn);
            System.out.println("Propiedades sincronizadas: " + sincronizadas);
            conn.close();
        }catch (SQLException sqlException){
            System.err.println("Error con la base de datos: " + sqlException.getMessage());
        }catch (IOException ioException){
            System.err.println("Error al acceder al archivo: " + ioException.getMessage());
        }
    }
}
