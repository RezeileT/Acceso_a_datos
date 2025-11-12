package p02accesoFicherosIO.ejerciciosOpcionales;

import java.io.*;

public class BackupIncremental {

    public static int backupIncremental(String carpetaOrigen, String carpetaDestino) throws IOException{
        System.out.println("Iniciando backup");

        File directorioOrigen = new File(carpetaOrigen);
        File directorioDestino = new File(carpetaDestino);
        int numeroArchivos = 0;

        if(!directorioOrigen.exists()){
            System.out.println("El directorio origen no existe");
            return 0;
        }

        File[] archivosOrigen = directorioOrigen.listFiles();

        if(archivosOrigen == null){
            System.out.println("No hay archivos en el directorio origen");
            return 0;
        }

        for(File archivo : archivosOrigen){
            System.out.println("Copiando archivo " + archivo.getName());
            copiarArchivo(archivo, directorioDestino);
            numeroArchivos++;
        }

        return numeroArchivos;
    }


    private static void copiarArchivo(File origen, File destino) throws IOException{
        File archivo = new File(origen.getAbsolutePath());
        String linea;
        String texto = "";
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
            while ((linea = br.readLine()) != null) {
                texto = texto + linea + "\n";
            }
        }

        String rutaEscrita = destino.getAbsolutePath()+ File.separator + archivo.getName();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaEscrita))){
            bw.write(texto);
        }
        System.out.println("Archivo copiado correctamente");
    }

    public static void main(String[] args) {
        try {
            int archivosCopiados = backupIncremental(
                    "./src/p02accesoFicherosIO/resources/documentos",
                    "./src/p02accesoFicherosIO/resources/backup"
            );
            System.out.println("Backup completado: " + archivosCopiados + " archivos");
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
