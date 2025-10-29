package ejerciciosFicheros;

import java.io.File;

public class ContenidoDirectorio {
    // Copia exacta del Fichero 1, pero mostando dentro del if el contenido de la carpeta, sus nombres (list(), length())
    public static void main(String[] args) {
        //Ruta del directorio como cadena de texto
        String dirPadre = "C:\\Users\\AlumnoAfternoon\\Documents\\PruebasJava";

        // Creacion de la instancia utilizando el constructor File
        File directorio = new File(dirPadre);

        // Verificar si el archivo existe y su es un directorio
        if (directorio.exists() && directorio.isDirectory()) {

            //Creación de un array del contenido dentro  de la carpeta
            String[] contenidos = directorio.list();

            for (int i = 0; i < contenidos.length; i++) {
                System.out.println(contenidos[i]);
            }
        } else {
            System.out.println("La siguiente ruta no es un directorio o no existe");
        }
    }
}
