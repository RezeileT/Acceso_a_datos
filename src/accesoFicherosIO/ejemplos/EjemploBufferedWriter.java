package accesoFicherosIO.ejemplos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EjemploBufferedWriter {
    public static void main(String[] args){
        String[] lineas = {
          "Encabezado del documento",
          "Primera linea",
          "Segunda linea",
          "Tercera linea",
        };
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/accesoFicherosIO/resources/salida_buffer.txt"))){
            for(String linea : lineas){
                bw.write(linea);
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Error al escribir el archivo");
        }
    }
}
