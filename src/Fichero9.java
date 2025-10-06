import java.io.File;
import java.util.Scanner;

public class Fichero9 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n--- Mi asistente de archivos ---");
            System.out.println("1. Verificar si un archivo existe");
            System.out.println("2. Explorar una carpeta");
            System.out.println("3. Crear una nueva carpeta");
            System.out.println("4. Crear un nuevo archivo");
            System.out.println("5. Trabajar con URIs");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    verificarArchivo();
                    break;
                case 2:
                    explorarCarpeta();
                    break;
                case 3:
                    crearCarpeta();
                    break;
                case 4:
                    crearArchivo();
                    break;
                case 5:
                    trabajarURI();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 6);

        scanner.close();

    }

    public static void verificarArchivo(){
        System.out.println("VERIFICAR ARCHIVO");
        System.out.print("Introduce el nombre del directorio: ");
        String directorio = scanner.next();
        System.out.print("Introduce el nombre del archivo: ");
        String archivo = scanner.next();

        File archivoComprovado = new File(directorio+"/"+archivo);

        if(archivoComprovado.exists()){
            System.out.println("El archivo existe en" + archivoComprovado.getAbsolutePath());
            System.out.println("Es un archivo archivo de: "  + archivoComprovado.length() + " bytes");
        }else{
            System.out.println("El archivo no existe en" + archivoComprovado.getAbsolutePath());
        }
    }
    public static void explorarCarpeta(){
        System.out.println("EXPLORAR ARCHIVO");

    }
    public static void crearCarpeta(){

    }
    public static void crearArchivo(){

    }
    public static void trabajarURI(){

    }
}
