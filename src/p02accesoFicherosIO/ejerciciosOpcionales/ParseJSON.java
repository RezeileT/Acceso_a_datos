package p02accesoFicherosIO.ejerciciosOpcionales;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ParseJSON {

    //Metodo para leer y procesar un archivo .json
    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {
        //Guarda los valores en el json
        Map<String, String> mapa = new HashMap<>();
        //Cuenta el número de variables
        int contadorLineas = 0;

        BufferedReader br = new BufferedReader(new FileReader(archivoJson));
        String linea;
        while ((linea = br.readLine()) != null) {
            //Si la línea contiene ":", formatea la clave y valor y los guarda
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

    //Metodo para escribir y procesar un archivo .json
    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/p02accesoFicherosIO/resources/json" + File.separator +archivoJson));
        bw.write("{\n");
        //Toma los datos guardados en el mapa y los formatea para escribirlos
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
        //Ruta del archivo .json
        String archivoJson = "src/p02accesoFicherosIO/resources/json/config.json";
        //El try debería recibir los throws IOException de leerJsonSimple y escribirJsonSimple
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
