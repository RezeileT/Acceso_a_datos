package p02accesoFicherosIO.ejerciciosObligatorios;
/*
 * Lee un archivo y cuenta palabras, líneas y caracteres
 * @param nombreArchivo ruta del archivo a analizar
 * @return objeto EstadisticasTexto con los resultados
 * @throws IOException si hay error al leer el archivo
 */
/*
 * Escribe las estadísticas en un archivo de salida
 * @param estadisticas objeto con las estadísticas
 * @param archivoSalida ruta donde guardar el resultado
 * @throws IOException si hay error al escribir
 */

//Importamos las librerias IO
import java.io.*;

//Creamos una clase para guardar los datos de los archivos leidos
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

    //Creamos un toString para escribir las estadisticas sin llamar a su get
    @Override
    public String toString() {
        return "Número de lineas: " + numeroLineas +
                "\nNúmero palabras: " + numeroPalabras +
                "\nNÚmero de caracteres: " + numeroCaracteres +
                "\nPalabra más larga: " + palabraMasLarga;
    }

    //Generamos getters y setter par acceder a los datos (aunque no los utilizemos)
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

    //Función para leer el archivo y guardar sus estadisticas
    public static EstadisticasTexto analizarArchivo(String ruta) throws IOException {

        //No hace falta crear un File, es para comprobar que exista
        File archivo = new File(ruta);
        String linea;
        int numLinea = 0;
        int numPalabras = 0;
        int numCaracteres = 0;
        String palabraMasLarga = "";

        BufferedReader br = new BufferedReader(new FileReader(ruta));
            //Comprobamos si el archivo existe
            if(archivo.exists()) {
                while ((linea = br.readLine()) != null) {
                    //Dividimos las líneas en palabras con .split() y las metemos en un array de Strings
                    String[] palabras = linea.split(" ");
                    //Contamos cuantas palabras hay en cada línea
                    for(String palabra : palabras){
                        //Contamos los caracteres de cada palabra (convertimos las palabras en un array de caracteres
                        for (char caracter : palabra.toCharArray()) {
                            if (caracter != ' ') {
                                numCaracteres++;
                            }
                        }
                        numPalabras++;
                        //Vemos si la longitud de la palabra es mas larga que la anterior
                        if (palabraMasLarga.length() < palabra.length()) {
                            palabraMasLarga = palabra;
                        }
                    }
                    numLinea++;
                }
            }else{
                System.out.println("El archivo no existe");
            }

        EstadisticasTexto et = new EstadisticasTexto(numLinea, numPalabras, numCaracteres, palabraMasLarga);

        escribirEstadisticas(et, "src/accesoFicherosIO/resources/estadisticas.txt");
        return et;
    }

    //Función para escribir un archivo con las estadisticas de otro archivo
    public static void escribirEstadisticas(EstadisticasTexto estadisticas, String archivoSalida) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida));
        bw.write(estadisticas.toString());

        // No hace falta utilizar los getter si tenemos un to String personalizado
        /*
        bw.write(estadisticas.getNumeroLineas());
        bw.write(estadisticas.getNumeroPalabras());
        bw.write(estadisticas.getNumeroCaracteres());
        bw.write(estadisticas.getPalabraMasLarga());
         */

        bw.close();

    }

    public static void main(String[] args) {
        System.out.println("***Estadisticas de archivo***");
        //El try debería recibir los throws IOException de analizarArchivo y scribirEstadisticas
        try {
            System.out.println(analizarArchivo("src\\accesoFicherosIO\\resources\\archivo.txt"));
        }catch (IOException e){
            System.err.println("Error al escribir o leer el archivo " + e.getMessage());
        }
    }
}
