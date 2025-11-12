package p01ejerciciosFicheros.ejercicios789;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Biblioteca {
    private static Scanner sc = new Scanner(System.in);

    private static String categoria = "";
    private static String nombre = "catalogo.txt";

    public static void main(String[] args) {
        organizarBiblioteca();
        verificarLibro();
    }

    public static void organizarBiblioteca() {
        System.out.println("***ORGANIZADOR DE BIBLIOTECA***");
        System.out.print("Ingrese el nombre del de la categoria: ");
        categoria = sc.nextLine();

        String directorio = "src/p01ejerciciosFicheros/resources/biblioteca" + File.separator + categoria;
        File directorioPadre = new File(directorio);

        String archivo = directorio + File.separator + nombre; // nombre puede estar vacío aquí
        File archivoHijo = new File(archivo);

        boolean fin = false;

        do {
            if (!directorioPadre.exists()) {
                directorioPadre.mkdir();
                System.out.println("Categoria " + categoria + " creada exitosamente");
                System.out.println("Creado en: " + directorioPadre.getAbsolutePath());

                if (!archivoHijo.exists()) {
                    System.out.println("El archivo no existe.");
                    try {
                        archivoHijo.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("El archivo creado por defecto.");
                }
            } else {
                System.out.println("El directorio y el archivo existen.");
                fin = true;
            }
        } while (!fin);
    }

    public static void verificarLibro() {
        System.out.print("Introduce la categoria del libro: ");
        categoria = sc.nextLine();
        System.out.print("Introduce el nombre del libro: ");
        nombre = sc.nextLine();

        String directorio = "src/p01ejerciciosFicheros/resources/biblioteca"+ File.separator + categoria;
        File directorioPadre = new File(directorio);

        String archivo = directorio + File.separator + nombre;
        File archivoHijo = new File(archivo);

        if (directorioPadre.exists() && archivoHijo.exists()) {
            System.out.println("El libro existe en: " + archivoHijo.getAbsolutePath());
        } else {
            System.out.println("El libro NO existe en: " + directorioPadre.getAbsolutePath());
        }

        sc.close();
    }
}

