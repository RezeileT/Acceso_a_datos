package p02accesoFicherosIO.ejemplos;

import java.io.IOException;
import java.io.RandomAccessFile;

public class EjemploRandomAccessFile {
    public static void main(String[] args) {
        try(RandomAccessFile raf = new RandomAccessFile("src/accesoFicherosIO/resources/datos.bin","rw")){
            raf.writeBytes("Inicio");
            raf.seek(20);
            raf.writeBytes("Medio");
            raf.seek(40);
            raf.writeBytes("Final");

            raf.seek(0);
            System.out.println("Posicion 0 : " + raf.readLine());

            raf.seek(20);
            System.out.println("Posicion 20 : " + raf.readLine());

            raf.seek(40);
            System.out.println("Posicion 40 : " + raf.readLine());

            //.length muestra el tamaño del archivo en bytes
            System.out.println("Tamaño del archivo : " + raf.length() + " bytes");
        }catch (IOException e){
            System.err.println("Error al acceder el archivo " + e.getMessage());
        }
    }
}
