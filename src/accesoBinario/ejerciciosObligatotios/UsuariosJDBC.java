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

    private static Statement sentencias;
    private static ResultSet resultados;
    private static Connection conexion = null;

    public static void crearTabla(Connection conn) throws SQLException {
        conn.createStatement().executeQuery("CREATE TABLE IF NOT EXISTS usuarios (" +
                "id int PRIMARY KEY AUTOINCREMENT," +
                "nombre varchar(100) NOT NULL," +
                "email varchar(100) NOT NULL," +
                "edad int);)");
    }

    public static int insertarUsuario(Connection conn, String nombre, String email, int edad) throws SQLException{
        resultados = conn.createStatement().executeQuery("SELECT id FROM usuarios WHERE email='"+email+"'");
        return resultados.getInt("id");
    }

    public static List<Usuario> buscarPorNombre(Connection conn, String nombre) throws SQLException{
        List<Usuario> usuarios = new ArrayList<>();
        ResultSet rs =  conn.createStatement().executeQuery("SELECT * FROM usuarios WHERE nombre='"+nombre+"'");
        while(rs.next()){
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setEmail(rs.getString("email"));
            usuario.setEdad(rs.getInt("edad"));
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public static boolean actualizarEmail(Connection conn, int id, String nuevoEmail) throws SQLException{
        String query = "UPDATE usuarios SET email='"+nuevoEmail+"' WHERE id="+id;
        sentencias = conn.createStatement();
        resultados = sentencias.executeQuery(query);
        return resultados.rowUpdated();
    }

    public static boolean eliminarUsuario(Connection conn, int id) throws SQLException{
        resultados = conn.createStatement().executeQuery("DELETE FROM usuarios WHERE id="+id);
        return resultados.rowDeleted();
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
