package accesoFicherosIO.ejemplos;

import java.io.FileReader;
import java.io.IOException;

public class EjemploFileReader {
    public static void main(String[] args) {
        int caracter;

        try (FileReader fr = new FileReader("src/accesoFicherosIO/resources/entrada.txt")) {
            while ((caracter = fr.read()) != -1) {
                System.out.print((char) caracter);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo" + e.getMessage());
        }
    }
}

