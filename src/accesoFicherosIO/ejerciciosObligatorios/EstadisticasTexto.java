package accesoFicherosIO.ejerciciosObligatorios;
/**
 * Lee un archivo y cuenta palabras, líneas y caracteres
 * @param nombreArchivo ruta del archivo a analizar
 * @return objeto EstadisticasTexto con los resultados
 * @throws IOException si hay error al leer el archivo
 */
/**
 * Escribe las estadísticas en un archivo de salida
 * @param estadisticas objeto con las estadísticas
 * @param archivoSalida ruta donde guardar el resultado
 * @throws IOException si hay error al escribir
 */

import java.io.*;

public class EstadisticasTexto {
    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres;
    private String palabraMasLarga;

    public EstadisticasTexto(int numeroLineas, int numeroPalabras, int numeroCaracteres, String palabraMasLarga) {
        this.numeroLineas = numeroLineas;
        this.numeroPalabras = numeroPalabras;
        this.numeroCaracteres = numeroCaracteres;
        this.palabraMasLarga = palabraMasLarga;
    }

    @Override
    public String toString() {
        return "Número de lineas: " + numeroLineas +
                "\nNúmero palabras: " + numeroPalabras +
                "\nNÚmero de caracteres: " + numeroCaracteres +
                "\nPalabra más larga: " + palabraMasLarga;
    }

    public int getNumeroLineas() {
        return numeroLineas;
    }

    public void setNumeroLineas(int numeroLineas) {
        this.numeroLineas = numeroLineas;
    }

    public int getNumeroPalabras() {
        return numeroPalabras;
    }

    public void setNumeroPalabras(int numeroPalabras) {
        this.numeroPalabras = numeroPalabras;
    }

    public int getNumeroCaracteres() {
        return numeroCaracteres;
    }

    public void setNumeroCaracteres(int numeroCaracteres) {
        this.numeroCaracteres = numeroCaracteres;
    }

    public String getPalabraMasLarga() {
        return palabraMasLarga;
    }

    public void setPalabraMasLarga(String palabraMasLarga) {
        this.palabraMasLarga = palabraMasLarga;
    }

    public static EstadisticasTexto analizarArchivo(String ruta){
        File archivo = new File(ruta);
        String linea;
        int numLinea = 0;
        int numPalabras = 0;
        int numCaracteres = 0;
        String palabraMasLarga = "";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))){
            if(archivo.exists()) {
                while ((linea = br.readLine()) != null) {
                    String[] palabras = linea.split(" ");
                    for(String palabra : palabras){
                        for (char caracter : palabra.toCharArray()) {
                            if (caracter != ' ') {
                                numCaracteres++;
                            }
                        }
                        numPalabras++;
                        if (palabraMasLarga.length() < palabra.length()) {
                            palabraMasLarga = palabra;
                        }
                    }

                    numLinea++;
                }
            }else{
                System.out.println("El archivo no existe");
            }
        }catch (IOException e){
            System.err.println("Error al leer el archivo " + e.getMessage());
        }

        EstadisticasTexto et = new EstadisticasTexto(numLinea, numPalabras, numCaracteres, palabraMasLarga);

        escribirEstadisticas(et, "src/accesoFicherosIO/resources/estadisticas.txt");
        return et;
    }

    public static void escribirEstadisticas(EstadisticasTexto estadisticas, String archivoSalida){
        String[] lineas = estadisticas.toString().split("\n");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))){
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        }catch (IOException e){
            System.err.println("Error al escribir el archivo " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("***Estadisticas de archivo***");
        System.out.println(analizarArchivo("C:\\Users\\sebas\\IdeaProjects\\Acceso_a_datos\\src\\accesoFicherosIO\\resources\\archivo.txt"));
    }
}
