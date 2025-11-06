package accesoBinario.ejerciciosObligatotios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Usuario {
    private int id;
    private String nombre;
    private String email;
    private int edad;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}

public class UsuariosJDBC {

    private static Statement sentencia;
    private static ResultSet resultado;
    private static Connection conexion = null;

    public static void crearTabla(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS usuarios (" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                "nombre varchar(100) NOT NULL," +
                "email varchar(100) NOT NULL," +
                "edad int)");
        System.out.println("Tabla usuarios creada");
    }

    public static int insertarUsuario(Connection conn, String nombre, String email, int edad) throws SQLException{
        String sql = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nombre);
        pstmt.setString(2, email);
        pstmt.setInt(3, edad);

        int filasAfectadas = pstmt.executeUpdate();
        System.out.println("Usuario insertado: " + nombre);
        return filasAfectadas;
    }

    public static List<Usuario> buscarPorNombre(Connection conn, String nombre) throws SQLException{
        List<Usuario> usuarios = new ArrayList<>();
        resultado =  conn.createStatement().executeQuery("SELECT * FROM usuarios WHERE nombre='"+nombre+"'");
        while(resultado.next()){
            Usuario usuario = new Usuario();
            usuario.setId(resultado.getInt("id"));
            usuario.setNombre(resultado.getString("nombre"));
            usuario.setEmail(resultado.getString("email"));
            usuario.setEdad(resultado.getInt("edad"));
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public static boolean actualizarEmail(Connection conn, int id, String nuevoEmail) throws SQLException{
        String query = "UPDATE usuarios SET email='"+nuevoEmail+"' WHERE id="+id;
        sentencia = conn.createStatement();
        int filasAfectadas = sentencia.executeUpdate(query);
        return filasAfectadas > 0;
    }

    public static boolean eliminarUsuario(Connection conn, int id) throws SQLException{
        return conn.createStatement().executeUpdate("DELETE FROM usuarios WHERE id="+id) > 0;
    }

    static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String usuario = "root";
        String password = "mysql";

        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            crearTabla(conexion);
            int id1 = insertarUsuario(conexion, "Juan Pérez", "juanperez@email.com", 25);
            int id2 = insertarUsuario(conexion, "María García", "mariagarcia@email.com", 30);
            List<Usuario> usuarios = buscarPorNombre(conexion, "Juan");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
            actualizarEmail(conexion, id1, "juan.nuevo@email.com");
            eliminarUsuario(conexion, id2);
        }catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        } finally{
            try {
                conexion.close();
            }catch (SQLException e){
                System.err.println("Error al cerrado de la coneción: " + e.getMessage());
            }
        }
    }
}
