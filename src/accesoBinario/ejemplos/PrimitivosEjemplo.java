package accesoBinario.ejemplos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrimitivosEjemplo {
    public static void main(String[] args) {
        String archivo = "src/accesoBinario/resources/datosPrimitivos.bin";

        // Escritura de datos primitivos
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            dos.writeInt(123);           // Escribir entero
            dos.writeDouble(45.67);      // Escribir double
            dos.writeUTF("Hola Mundo");  // Escribir String en formato UTF
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lectura de datos primitivos
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int entero = dis.readInt();          // Leer entero
            double decimal = dis.readDouble();   // Leer double
            String texto = dis.readUTF();         // Leer String UTF

            System.out.println("Entero: " + entero);
            System.out.println("Decimal: " + decimal);
            System.out.println("Texto: " + texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}