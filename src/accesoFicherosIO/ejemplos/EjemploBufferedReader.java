package accesoFicherosIO.ejemplos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EjemploBufferedReader {
    public static void main(String[] args) {
        String linea;
        int numLinea = 1;

        try(BufferedReader br = new BufferedReader(new FileReader("src/accesoFicherosIO/resources/entrada.txt"))){
            while ((linea = br.readLine()) != null){
                System.out.println(linea + numLinea);
                numLinea++;
            }
        }catch (IOException e){
            System.out.println("Error al abrir el archivo" + e.getMessage());
        }
    }
}
