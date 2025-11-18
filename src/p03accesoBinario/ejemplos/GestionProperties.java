package p03accesoBinario.ejemplos;

import java.io.*;
import java.util.Properties;

public class GestionProperties {
    public static void main(String[] args) {
        Properties props = new Properties();
        File archivo = new File("src/p03accesoBinario/resources/configEjemplo.properties");

        // Intentar cargar desde archivo existente
        try (FileInputStream fis = new FileInputStream(archivo)) {
            props.load(fis);
            System.out.println("Archivo de configuración cargado.");
        } catch (FileNotFoundException e) {
            // Archivo no existe: definir propiedades por defecto
            System.out.println("Archivo no encontrado. Creando propiedades por defecto.");
            props.setProperty("usuario", "admin");
            props.setProperty("tema", "oscuro");
            props.setProperty("timeout", "30");
            // Guardar archivo de configuración
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                props.store(fos, "Archivo de configuración por defecto");
                System.out.println("Archivo de configuración creado.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Leer propiedades con valor por defecto si no existen
        String usuario = props.getProperty("usuario", "no-definido");
        String tema = props.getProperty("tema", "claro");
        String timeout = props.getProperty("timeout", "10");

        System.out.println("Usuario: " + usuario);
        System.out.println("Tema: " + tema);
        System.out.println("Timeout: " + timeout);
    }
}
