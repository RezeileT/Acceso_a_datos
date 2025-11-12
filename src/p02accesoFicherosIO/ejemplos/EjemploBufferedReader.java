package p02accesoFicherosIO.ejemplos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EjemploBufferedReader {
    public static void main(String[] args) {
        //Inicializamos un String para guardar las líneas que lea el BufferedReader
        String linea;
        //Esta variable cuenta las líneas del archivo
        int numLinea = 1;

        try(BufferedReader br = new BufferedReader(new FileReader("src/accesoFicherosIO/resources/entrada.txt"))){
            //Se recorre el archivo línea por línea hasta encontrar una línea vacía (null)
            while ((linea = br.readLine()) != null){
                System.out.println(linea + numLinea);
                numLinea++;
            }
        }catch (IOException e){
            System.out.println("Error al abrir el archivo" + e.getMessage());
        }
    }
}
