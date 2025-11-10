package accesoBinario.ejerciciosObligatotios;

import java.io.*;
import java.util.Properties;

public class ConfigurandoProperties {
    public static Properties cargarConfiguracion(String archivo) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            props.load(fis);
        }
        return props;
    }
    public static String getString(Properties props, String clave, String valorDefecto){
        return props.getProperty(clave, valorDefecto);
    }
    public static int getInt(Properties props, String clave, int valorDefecto){
        return Integer.parseInt(props.getProperty(clave, String.valueOf(valorDefecto)));
    }
    public static boolean getBoolean(Properties props, String clave, boolean valorDefecto){
        return Boolean.parseBoolean(props.getProperty(clave, String.valueOf(valorDefecto)));
    }
    public static void guardarConfiguracion(Properties props, String archivo, String comentario) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.newLine();
        props.store(bw, comentario);
        bw.close();
    }
    public static void mostrarConfiguracion(Properties props){
        System.out.println(props.toString());
    }

    public static void main(String[] args) {
        String ruta = "src/accesoBinario/resources";
        try {
            Properties config = cargarConfiguracion(ruta + File.separator + "app.properties");
            String dbHost = getString(config, "db.host", "localhost");
            int dbPort = getInt(config, "db.port", 3306);
            boolean debug = getBoolean(config, "app.debug", false);
            System.out.println("=== Configuración Actual ===");
            mostrarConfiguracion(config);
            config.setProperty("app.idioma", "es");
            config.setProperty("ui.tema", "oscuro");
            config.setProperty("db.port", "3307");
            guardarConfiguracion(config, ruta + File.separator + "app.properties", "Configuración de Mi Aplicación");
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }

    }
}
