package p02accesoFicherosIO.ejerciciosObligatorios;

import java.io.*;

//Creamos una clase para guardar los datos de los archivos leídos
public class EstadisticasTexto {
    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres;
    private String palabraMasLarga;

    //Creamos un constructor para poder meter los datos
    public EstadisticasTexto(int numeroLineas, int numeroPalabras, int numeroCaracteres, String palabraMasLarga) {
        this.numeroLineas = numeroLineas;
        this.numeroPalabras = numeroPalabras;
        this.numeroCaracteres = numeroCaracteres;
        this.palabraMasLarga = palabraMasLarga;
    }

    //Creamos un toString para escribir las estadisticas sin llamar a los getters
    @Override
    public String toString() {
        return "Número de lineas: " + numeroLineas +
                "\nNúmero palabras: " + numeroPalabras +
                "\nNÚmero de caracteres: " + numeroCaracteres +
                "\nPalabra más larga: " + palabraMasLarga;
    }

    //Función para leer el archivo y guardar sus estadisticas
    public static EstadisticasTexto analizarArchivo(String ruta) throws IOException {

        //No hace falta crear un File, es para comprobar que exista
        File archivo = new File(ruta);
        //Variable para guardar las líneas del archivo
        String linea;
        //Total de líneas del archivo
        int numLinea = 0;
        //Total de palabras del archivo
        int numPalabras = 0;
        //Total de caracteres del archivo
        int numCaracteres = 0;
        //Guardará la palabra más larga
        String palabraMasLarga = "";

        BufferedReader br = new BufferedReader(new FileReader(ruta));
        //Comprobamos si el archivo existe
        if (!archivo.exists()) {
            System.out.println("El archivo no existe");
            return new EstadisticasTexto(0, 0, 0, "");
        }

        while ((linea = br.readLine()) != null) {
            //Dividimos las líneas en palabras con .split() y las metemos en un array de Strings
            String[] palabras = linea.split(" ");
            //Contamos cuantas palabras hay en cada línea
            for (String palabra : palabras) {
                //Contamos el total de caracteres
                numCaracteres += contarCaracteres(palabra);
                //Contamos el total de palabras
                numPalabras++;
                //Vemos si la longitud de la palabra es mas larga que la anterior
                if (palabraMasLarga.length() < palabra.length()) {
                    palabraMasLarga = palabra;
                }
            }
            //Contamos el total de líneas
            numLinea++;
        }
        //No utilizo un try para cerrar el buffer para evitar anidar mucho el código
        br.close();
        //Creamos un objeto EstadisticasTexto y se lo pasamos a escribirEstadisticas para guardarlo en un archivo txt
        EstadisticasTexto et = new EstadisticasTexto(numLinea, numPalabras, numCaracteres, palabraMasLarga);
        escribirEstadisticas(et, "src/p02accesoFicherosIO/resources/estadisticas.txt");
        return et;
    }

    //Metodo con el que contamos los caracteres de cada palabra (convertimos las palabras en un array de caracteres)
    public static int contarCaracteres(String palabra) {
        int caracteres = 0;
        for (char caracter : palabra.toCharArray()) {
            if (caracter != ' ') {
                caracteres++;
            }
        }
        return caracteres;
    }

    //Función para escribir un archivo con las estadisticas de otro archivo
    public static void escribirEstadisticas(EstadisticasTexto estadisticas, String archivoSalida) throws IOException {
        //Utilizamos el try para cerrar automáticamente el buffer
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))){
            bw.write(estadisticas.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println("***Estadisticas de archivo***");

        //El try debería recibir los throws IOException de analizarArchivo y scribirEstadisticas
        try {
            System.out.println(analizarArchivo("src\\p02accesoFicherosIO\\resources\\archivo.txt"));
        } catch (IOException e) {
            System.err.println("Error al escribir o leer el archivo " + e.getMessage());
        }
    }
}
