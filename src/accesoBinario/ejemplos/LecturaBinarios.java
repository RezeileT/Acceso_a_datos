package accesoBinario.ejemplos;

import java.io.FileInputStream;
import java.io.IOException;

public class LecturaBinarios {
    static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("src/accesoBinario/resources/datos.bin")) {
            int byteLectura;
            while ((byteLectura = fis.read()) != -1) {
                System.out.println(byteLectura);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
