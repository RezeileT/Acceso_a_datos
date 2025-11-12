package p02accesoFicherosIO.ejerciciosOpcionales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * Lee un archivo .env y carga las variables
 * @param archivoEnv ruta del archivo .env
 * @return Map con las variables cargadas
 * @throws IOException si hay error de lectura
 */
/*
 * Obtiene el valor de una variable de entorno
 * @param clave nombre de la variable
 * @param valorPorDefecto valor si la variable no existe
 * @return valor de la variable o valorPorDefecto
 */

public class CargaEnv {
    private static int contadorVariables = 0;
    private static Map<String, String> env;

    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {
        Map<String, String> mapa = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(archivoEnv));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.contains("=")) {
                String[] variables = linea.split("=");
                mapa.put(variables[0].trim(), variables[1].trim());
                contadorVariables++;
            }
        }
        br.close();
        System.out.println("Variables cargadas: " + contadorVariables);
        return mapa;
    }

    public static String getEnv(String clave, String valorPorDefecto){
        return env.getOrDefault(clave, valorPorDefecto);
    }

    public static void main(String[] args) {
        try {
            env = cargarEnv("src/accesoFicherosIO/resources/env/.env");
            System.out.println("Base de datos: " + env.get("DB_HOST") + ":" + env.get("DB_PORT"));
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        String debug = getEnv("DEBUG", "false");
        System.out.println("Debug mode: " + debug);
    }
}
