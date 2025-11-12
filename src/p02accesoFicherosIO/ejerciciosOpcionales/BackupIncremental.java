package p02accesoFicherosIO.ejerciciosOpcionales;

import java.io.*;

public class BackupIncremental {

    //Metodo que verifica los directorios origen y destino
    public static int backupIncremental(String carpetaOrigen, String carpetaDestino) throws IOException{
        System.out.println("Iniciando backup");

        File directorioOrigen = new File(carpetaOrigen);
        File directorioDestino = new File(carpetaDestino);
        //Contara el número de archivos copiados
        int numeroArchivos = 0;

        //Verifica si existe la carpeta origen
        if(!directorioOrigen.exists()){
            System.out.println("El directorio origen no existe");
            return 0;
        }

        //Verifica si existe la carpeta destino, si no existe la crea
        if (!directorioDestino.exists()) {
            System.out.println("El directorio destino no existe, creándolo...");
            directorioDestino.mkdir();
        }

        //Guarda los archivos que contenga el directorio (si los tiene)
        File[] archivosOrigen = directorioOrigen.listFiles();

        //Verifica si tiene directorios
        if(archivosOrigen == null){
            System.out.println("No hay archivos en el directorio origen");
            return 0;
        }

        //Recorre los archivos guardados en archivosOrigen y llama al metodo copiarArchivo para hacer el backup
        for(File archivo : archivosOrigen){
            System.out.println("Copiando archivo " + archivo.getName());
            copiarArchivo(archivo, directorioDestino);
            numeroArchivos++;
        }

        return numeroArchivos;
    }

    //Metodo para copiar los archivos
    private static void copiarArchivo(File origen, File destino) throws IOException{
        //Ruta del archivo
        File archivo = new File(origen.getAbsolutePath());
        //Auxiliares para leer y escribir
        String linea;
        String texto = "";
        //Lee el archivo
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
            while ((linea = br.readLine()) != null) {
                texto = texto + linea + "\n";
            }
        }
        //Escribe la copia
        String rutaEscrita = destino.getAbsolutePath()+ File.separator + archivo.getName();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaEscrita))){
            bw.write(texto);
        }
        System.out.println("Archivo copiado correctamente");
    }

    public static void main(String[] args) {
        //El try debería recibir los throws IOException desde backupIncremental
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
