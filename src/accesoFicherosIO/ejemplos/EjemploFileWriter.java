package accesoFicherosIO.ejemplos;

import java.io.FileWriter;
import java.io.IOException;

public class EjemploFileWriter {

    public static void main(String[] args) {
        String contenido = "Primera linea \nSegunda linea \nTercera linea";

        try (FileWriter fw = new FileWriter("src/accesoFicherosIO/ejemplos/salida.txt")) {
            fw.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo" + e.getMessage());
        }
    }
}
