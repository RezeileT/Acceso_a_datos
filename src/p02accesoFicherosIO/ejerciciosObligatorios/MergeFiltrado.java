package p02accesoFicherosIO.ejerciciosObligatorios;

import java.io.*;

public class MergeFiltrado {
    //Variable para guardar los archivos/directorios del directorio que se quiera leer
    private static String[] archivoEntrada;
    //Archivo que se contiene las frases filtradas
    private static final String archivoSalida = "combinado.txt";
    //Criterio del filtro
    private static final String FILTRO = "Java ";
    //Contador para mostar en consola el número de líneas generadas
    private static int numLineasEscritas = 0;
    //Variable para guardar el texto a escribir
    private static String textoEscrito = "";

    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {
        File directorio = new File("src/p02accesoFicherosIO/resources/filtro");

        //Comprobamos si el archivo existe
        if (!directorio.exists()) {
            System.out.println("El directorio no existe");
            return 0;
        }
        //Comprobamos si es un directorio
        if (!directorio.isDirectory()) {
            System.out.println("No es un directorio");
            return 0;
        }

        archivosEntrada = directorio.list();
        //Recorremos los archivos
        for (String s : archivosEntrada) {
            //Creamos una ruta para acceder a los archivos
            String ruta = directorio + File.separator + s;
            System.out.println("Procesando: " + s);
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea = br.readLine()) != null) {
                //Comprovamos si la línea contiene lo del filtro, en caso de que sea asi lo guardamos en textoEscrito
                if (cumpleFiltro(linea, filtro)) {
                    textoEscrito = textoEscrito + linea + "\n";
                    numLineasEscritas++;
                }
            }
            br.close();
        }


        escribirArchivo(textoEscrito, "src/p02accesoFicherosIO/resources", archivoSalida);

        return numLineasEscritas;
    }

    public static void escribirArchivo(String textoEscrito, String ruta, String archivoSalida) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta + File.separator + archivoSalida))) {
            bw.write(textoEscrito);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }


    private static boolean cumpleFiltro(String linea, String filtro) {
        String[] palabras = linea.split(" ");
        boolean resultado = false;
        for (String palabra : palabras) {
            if (palabra.toLowerCase().contentEquals(filtro.toLowerCase().trim())) {
                resultado = true;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        int num = 0;
        try {
            num = combinarArchivos(archivoEntrada, archivoSalida, FILTRO);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Número de líneas escritas: " + String.valueOf(num));
    }


}
