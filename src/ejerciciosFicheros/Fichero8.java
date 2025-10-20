package ejerciciosFicheros;

import java.io.File;
import java.net.URI;
import java.util.Scanner;

public class Fichero8 {
    private static Scanner sc = new Scanner(System.in);
    private static String carpeta;

    public static void main(String[] args) {
        explorarCarpeta();
    }

    public static void explorarCarpeta() {
        System.out.println("***Explorador de carpetas***");
        System.out.print("Introduce la carpeta que quieras explorar: ");
        carpeta = sc.nextLine();
        System.out.println("Explorando: " + carpeta);

        analizarElemento(carpeta);
        convertirAURI(carpeta);
    }

    public static void analizarElemento(String carpeta) {
        File file = new File(carpeta);
        int contadorElementos = 0;
        int contadorSubElementos;

        if (file.exists() && file.isDirectory()) {
            File[] elementos = file.listFiles();
            if (elementos != null) {
                for (File elemento : elementos) {
                    if (elemento.isDirectory()) {
                        File[] subelementos = elemento.listFiles();
                        contadorSubElementos = 0;
                        for (File subelemento : subelementos) {
                            contadorSubElementos++;
                        }
                        System.out.println(elemento.getName() + " - El directorio tiene "  + contadorSubElementos + " elementos");
                    } else if (elemento.isFile()) {
                        System.out.println(elemento.getName() + " - Archivo: " +elemento.length() + " bytes");
                    }
                    contadorElementos++;
                }
            } else {
                System.out.println("La carpeta está vacía o no se puede acceder.");
            }
        } else {
            System.out.println("La ruta introducida no existe o no es una carpeta.");
        }
        System.out.println("Total de elementos encontrados : " + contadorElementos);
    }

    public static void convertirAURI(String carpeta) {
        System.out.println("Conversión a URI");
        File carpetaF = new File(carpeta);

        URI uri = carpetaF.toURI();
        System.out.println("URI equivalente: " + uri);

        if (carpetaF.exists()) {
            if (carpetaF.isDirectory()) {
                System.out.println("La ruta corresponde a un directorio.");
            } else if (carpetaF.isFile()) {
                System.out.println("La ruta corresponde a un archivo.");
            }
        } else {
            System.out.println("La URI no existe.");
        }
    }
}
