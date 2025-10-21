package accesoFicherosIO.ejemplos;

import java.io.FileReader;
import java.io.IOException;

public class EjemploFileReader {
    public static void main(String[] args) {
        //Los caracteres que devuelve el FileReader son enteros
        int caracter;

        try (FileReader fr = new FileReader("src/accesoFicherosIO/resources/entrada.txt")) {
            //Se recorre el archivo caracter por caracter hasta encontrar un caracter vacío (-1)
            while ((caracter = fr.read()) != -1) {
                System.out.print((char) caracter);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo" + e.getMessage());
        }
    }
}

