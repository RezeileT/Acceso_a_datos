package p02accesoFicherosIO.ejerciciosObligatorios;

import java.io.*;

/*
 * Combina múltiples archivos en uno solo, filtrando líneas
 * @param archivosEntrada array con las rutas de los archivos a combinar
 * @param archivoSalida ruta del archivo resultado
 * @param filtro palabra que debe contener la línea para incluirse (null = todas)
 * @return número total de líneas escritas
 * @throws IOException si hay error de lectura/escritura
 */
/*
 * Verifica si una línea cumple el criterio de filtrado
 * @param línea a evaluar
 * @param filtro criterio de búsqueda (null = siempre true)
 * @return true si la línea debe incluirse
 */
public class MergeFiltrado {
    private static String[] archivoEntrada;
    private static final String archivoSalida = "combinado.txt";
    private static final String FILTRO = "Java ";
    private static int numLineasEscritas = 0;
    private static String textoEscrito = " ";

    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {
        File directorio = new File("src/accesoFicherosIO/resources/filtro");
        ;
        //Comprobamos si el archivo existe y de si es un directorio
        if (directorio.exists() && directorio.isDirectory()) {
            archivosEntrada = directorio.list();
            for (int i = 0; i < archivosEntrada.length; i++) {
                String ruta = directorio + File.separator + archivosEntrada[i];
                System.out.println("Procesando: " + archivosEntrada[i]);
                BufferedReader br = new BufferedReader(new FileReader(ruta));
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (cumpleFiltro(linea, filtro)) {
                        textoEscrito = textoEscrito + linea + "\n";
                        numLineasEscritas++;
                    }
                }
                br.close();
            }
        } else {
            System.out.println("La siguiente ruta no es un directorio o no existe");
        }
        escribirArchivo(textoEscrito, "src/accesoFicherosIO/resources", archivoSalida);

        return numLineasEscritas;
    }
    public static void escribirArchivo(String textoEscrito, String ruta, String archivoSalida) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ruta + File.separator + archivoSalida))) {
            bw.write(textoEscrito);
        }catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }


    private static boolean cumpleFiltro(String linea, String filtro){
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
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Número de líneas escritas: " + String.valueOf(num));
    }



}
