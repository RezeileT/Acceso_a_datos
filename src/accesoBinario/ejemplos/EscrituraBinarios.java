package accesoBinario.ejemplos;

import java.io.FileOutputStream;
import java.io.IOException;

public class EscrituraBinarios {
    public static void main(String[] args) {
        byte[] datos = {10, 20, 30, 40, 50}; // Array de bytes a escribir

        try (FileOutputStream fos = new FileOutputStream("src/accesoBinario/resources/salida.bin")) {
            fos.write(datos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores de E/S
        }
    }
}
