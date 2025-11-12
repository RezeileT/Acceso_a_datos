package p02accesoFicherosIO.ejemplos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EjemploBufferedWriter {
    public static void main(String[] args){
        //Inicializamos un array de String para escribir línea por línea
        String[] lineas = {
          "Encabezado del documento",
          "Primera linea",
          "Segunda linea",
          "Tercera linea",
        };
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/accesoFicherosIO/resources/salida_buffer.txt"))){
            //Se recorre el array línea por línea
            for(String linea : lineas){
                //Se escribe la línea en el archivo
                bw.write(linea);
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Error al escribir el archivo");
        }
    }
}
