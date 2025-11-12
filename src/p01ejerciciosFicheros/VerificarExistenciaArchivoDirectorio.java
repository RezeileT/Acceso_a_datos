package p01ejerciciosFicheros;

import java.io.File;

public class VerificarExistenciaArchivoDirectorio {
    public static void main(String[] args) {
        // Directorio padre que acabamos de crear en documentos
        File directorioPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\PruebasJava");

        // Nombre o ruta relativa al fichero que acabamos de crear
        String nomHijo = "hijo.txt";

        // Creo una instancia File utilizando el constructor y la variable de arriba
        File archivo = new File(directorioPadre, nomHijo);

        // Verificar si el archivo existe
        if(archivo.exists()){
            // Si el archivo existe se muestra un mensaje de la ruta especificada
            System.out.println("El archivo existe en la ruta " + archivo.getAbsolutePath());
        }else{
            // Si el archivo NO existe se muestra un mensaje de la ruta especificad
            System.out.println("El archivo NO existe en la ruta " + archivo.getAbsolutePath());
        }
    }
}
