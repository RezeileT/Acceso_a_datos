package p02accesoFicherosIO.ejemplos;

import java.io.FileWriter;
import java.io.IOException;

public class EjemploFileWriter {

    public static void main(String[] args) {
        //Inicializamos un String para escribir caracter por caracter
        String contenido = "Primera linea \nSegunda linea \nTercera linea";

        try (FileWriter fw = new FileWriter("src/accesoFicherosIO/resources/salida.txt")) {
            //Se escribe el String
            fw.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo" + e.getMessage());
        }
    }
}
