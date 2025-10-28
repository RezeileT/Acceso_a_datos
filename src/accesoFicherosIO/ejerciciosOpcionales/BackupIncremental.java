package accesoFicherosIO.ejerciciosOpcionales;

import java.io.*;

public class BackupIncremental {

    public static int backupIncremental(String carpetaOrigen, String carpetaDestino, String archivoControl) throws IOException{
        System.out.println("Iniciando backup");

        File archivoControlCreado = new File(archivoControl);
        File directorioOrigen = new File(carpetaOrigen);
        File directorioDestino = new File(carpetaDestino);
        int numeroArchivos = 0;

        long datosControl = leerUltimoBackup(archivoControl);

        if(datosControl == 0){
            System.out.println("Último backup: nunca");
            archivoControlCreado.createNewFile();
        }else{
            System.out.println("Último backup: " + datosControl);
        }

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

    private static long leerUltimoBackup(String archivoControl) throws IOException {
        if(archivoControl != "") {
            File ultimoModificado = new File(archivoControl);
            return ultimoModificado.lastModified();
        }else{
            return 0;
        }
    }

    private static void copiarArchivo(File origen, File destino) throws IOException{
        File archivo = new File(origen.getAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        String texto = "";
        while ((linea = br.readLine()) != null) {
            texto = texto + linea + "\n";
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(destino.getAbsolutePath()+ File.separator + archivo.getName()));
        bw.write(texto);
        bw.close();
        System.out.println("Archivo copiado correctamente");
    }

    static void main(String[] args) {
        try {
            int archivosCopiados = backupIncremental(
                    "./src/accesoFicherosIO/resources/documentos",
                    "./src/accesoFicherosIO/resources/backup",
                    "./src/accesoFicherosIO/resources/backup/.lastbackup"
            );
            System.out.println("Backup completado: " + archivosCopiados + " archivos");
            System.out.println("Registro actualizado: " + leerUltimoBackup("./backup/lastbackup"));
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
