package p01ejerciciosFicheros;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class MenuArchivos {
    private static Scanner sc = new Scanner(System.in);
    private static String input = null;
    private static File file = null;

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n--- Mi asistente de archivos ---" +
                    "\n1. Verificar si un archivo existe" +
                    "\n2. Explorar una carpeta" +
                    "\n3. Crear una nueva carpeta" +
                    "\n4. Crear un nuevo archivo" +
                    "\n5. Trabajar con URIs" +
                    "\n6. Salir" +
                    "\nElige una opción: ");
            opcion = sc.nextInt();

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

        sc.close();
    }

    public static void verificarArchivo(){
        System.out.println("VERIFICAR ARCHIVO");
        System.out.print("Introduce el nombre del directorio: ");
        input = sc.next();
        System.out.print("Introduce el nombre del archivo: ");
        String archivo = sc.next();

        file = new File(input +"/"+archivo);

        if(file.exists()){
            System.out.println("El archivo existe en " + file.getAbsolutePath());
            System.out.println("Es un archivo archivo de: "  + file.length() + " bytes");
        }else{
            System.out.println("El archivo no existe en " + file.getAbsolutePath());
        }
    }
    public static void explorarCarpeta(){
        System.out.println("\nEXPLORAR ARCHIVO");
        System.out.print("\nIntroduce el nombre del directorio: ");
        input = sc.next();

        file = new File(input);

        int contadorElementos = 0;
        int contadorSubElementos;
        int numerador = 0;

        if (file.exists() && file.isDirectory()) {
            File[] elementos = file.listFiles();
            if (elementos != null) {
                for (File elemento : elementos) {
                    if (elemento.isDirectory()) {
                        numerador++;
                        File[] subelementos = elemento.listFiles();
                        contadorSubElementos = 0;
                        for (File subelemento : subelementos) {
                            contadorSubElementos++;
                        }
                        System.out.println(numerador + ". " + elemento.getName() + " - El directorio tiene "  + contadorSubElementos + " elementos");
                    } else if (elemento.isFile()) {
                        System.out.println(numerador + ". " + elemento.getName() + " - Archivo: " +elemento.length() + " bytes");
                    }
                    contadorElementos++;
                }
            } else {
                System.out.println("La carpeta está vacía o no se puede acceder.");
            }
        } else {
            System.out.println("La ruta introducida no existe o no es una carpeta.");
        }
        System.out.println("\nTotal de elementos encontrados : " + contadorElementos);
    }

    public static void crearCarpeta(){
        System.out.println("***CREAR CARPETA***");
        System.out.print("Introduce el nombre del directorio y de la carpeta: ");
        input = sc.next();

        file = new File(input);

        if(!file.exists()){
            file.mkdir();
            System.out.println("\nCarpeta creada correctamente.");
        } else {
            System.out.println("\nLa carpeta ya existe.");
        }
    }
    public static void crearArchivo(){
        System.out.println("***CREAR ARCHIVO***");
        System.out.print("Introduce el nombre del directorio y del archivo: ");
        input = sc.next();

        file = new File(input);

        if(!file.exists()){
            try {
                file.createNewFile();
                System.out.println("\nArchivo creado correctamente.");
            } catch (IOException e) {
                System.out.println("Error creando archivo." + e.getMessage());
            }
        } else {
            System.out.println("\nLa carpeta ya existe.");
        }
    }
    public static void trabajarURI(){
        System.out.println("***TRABAJAR CON URIs***");
        int opcion;

        do{
            System.out.println("Introduce una opción: ");
            System.out.println("1. Verificar una URI existente");
            System.out.println("2. Convertir ruta a URI");
            System.out.println("3. Salir");
            opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    verificarURI();
                    break;
                case 2:
                    convertirURI();
                    break;
                case 3:
                    System.out.println("Volviendo");
                    break;
            }

        }while(opcion != 3);
    }
    public static void verificarURI(){
        System.out.print("Introduce una URI (ejemplo: file:///C:ruta/archivo.txt): ");
        input = sc.next();

        try{
            URI uri = new URI(input);
            File file = new File(uri);
            if (file.exists()) {
                if (file.isDirectory()) {
                    System.out.println("La ruta corresponde a un directorio en: " + uri);
                } else if (file.isFile()) {
                    System.out.println("La ruta corresponde a un archivo en: " + uri);
                }
            } else {
                System.out.println("La URI no existe.");
            }

        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    public static void convertirURI(){
        System.out.print("Introduce la ruta a convertir: ");
        input = sc.next();

        file = new File(input);
        URI uri = file.toURI();

        System.out.println("Ruta original: " + input);
        System.out.println("URI generado: " + uri);
    }
}