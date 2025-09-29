import java.io.File;

public class Fichero2 {
    public static void main(String[] args) {
        // Directorio padre que acabamos de crear en documentos
        File ruta = new File("C:\\Users\\AlumnoAfternoon\\Documents\\PruebasJava");

        // Verificar si la ruta existe
        if(ruta.exists()){
            // Verificar si la ruta especificada es un directorio
            if(ruta.isDirectory()){
                //Si en la ruta es un directorio, nos muestra un mensaje en pantalla que la ruta especificada es un directorio
                System.out.println("La ruta presenta un directorio en " +  ruta.getAbsolutePath());
            }else if(ruta.isFile()){
                //Si en la ruta es un archivo, nos muestra un mensaje en pantalla que la ruta especificada es un archivo
                System.out.println("La ruta presenta un archivo en " +  ruta.getAbsolutePath());
            }
            // Verificar si la ruta especificada es un directorio
        }
    }
}
