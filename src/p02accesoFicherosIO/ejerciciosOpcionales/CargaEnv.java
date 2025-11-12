package p02accesoFicherosIO.ejerciciosOpcionales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CargaEnv {
    //Cuenta las variables en el archivo
    private static int contadorVariables = 0;
    //Guarda las variables y sus valores
    private static Map<String, String> env;

    //Metodo que lee el archivo .env y devuelve las variables sy valores
    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {
        Map<String, String> mapa = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(archivoEnv));
        String linea;
        while ((linea = br.readLine()) != null) {
            //Si la linea contiene "=", pondrá en el mapa la variable y valor
            if (linea.contains("=")) {
                //Se guarda la variable y el valor
                String[] variableValor = linea.split("=");
                //variableValor[0] = variable, variableValor[1] = valor
                mapa.put(variableValor[0].trim(), variableValor[1].trim());
                contadorVariables++;
            }
        }
        br.close();
        System.out.println("Variables cargadas: " + contadorVariables);
        return mapa;
    }

    //Obtiene los datos del mapa
    public static String getEnv(String clave, String valorPorDefecto){
        return env.getOrDefault(clave, valorPorDefecto);
    }

    public static void main(String[] args) {
        //El try debería recibir los throws IOException de cargaEnv
        try {
            env = cargarEnv("src/p02accesoFicherosIO/resources/env/.env");
            System.out.println("Base de datos: " + env.get("DB_HOST") + ":" + env.get("DB_PORT"));
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        String debug = getEnv("DEBUG", "false");
        System.out.println("Debug mode: " + debug);
    }
}
