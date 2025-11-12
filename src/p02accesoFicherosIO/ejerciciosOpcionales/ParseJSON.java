package p02accesoFicherosIO.ejerciciosOpcionales;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * Lee un archivo JSON y extrae pares clave valor simples
 * @param archivoJson ruta del archivo JSON
 * @return Map con las claves y valores parseados
 * @throws IOException si hay error de lectura
 */
/*
 * Escribe un Map como archivo JSON formateado
 * @param datos Map con los datos a escribir
 * @param archivoJson ruta del archivo de salida
 * @throws IOException si hay error de escritura
 */

public class ParseJSON {

    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {
        Map<String, String> mapa = new HashMap<>();
        int contadorLineas = 0;

        BufferedReader br = new BufferedReader(new FileReader(archivoJson));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.contains(":")) {
                String[] partes = linea.split(":");
                String clave = partes[0].trim().replace("\"", "").replace(",", "");
                String valor = partes[1].trim().replace("\"", "").replace(",", "");
                mapa.put(clave, valor);
                contadorLineas++;
            }
        }
        br.close();
        System.out.println("JSON leído: " + contadorLineas + " propiedades");
        return mapa;
    }

    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/accesoFicherosIO/resources/json" + File.separator +archivoJson));
        bw.write("{\n");
        for (int i = 0; i < datos.size(); i++) {
            if (i < datos.size() - 1) {
                bw.write("\"" + datos.keySet().toArray()[i] + "\" " + ":" + " \"" + datos.values().toArray()[i] + "\",");
            }else
                bw.write("\"" + datos.keySet().toArray()[i] + "\" " + ":" + " \"" + datos.values().toArray()[i] + "\"");
            bw.newLine();
        }
        bw.write("}");
        bw.close();
        System.out.println("JSON escrito: " + datos.size() + " propiedades en " + archivoJson);
    }

    public static void main(String[] args) {
        String archivoJson = "src/accesoFicherosIO/resources/json/config.json";
        try {
            Map<String, String> config = leerJsonSimple(archivoJson);
            System.out.println("Host: " + config.get("host"));
            config.put("version", "1.0");
            escribirJsonSimple(config, "config_nuevo.json");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
