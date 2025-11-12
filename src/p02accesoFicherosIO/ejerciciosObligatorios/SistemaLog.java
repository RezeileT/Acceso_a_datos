package p02accesoFicherosIO.ejerciciosObligatorios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class SistemaLog {
    //Nombre del archivo
    private String archivoLog;
    //Tamaño maximo del archivo
    private long tamanoMaximo;
    //Número que se añade al nombre cuando se rota
    private int numeroRotacion;
    //Ruta donde se guardaran los archivos
    String ruta = "src/p02accesoFicherosIO/resources/app";

    //El constructor guardara el número a rotar
    public SistemaLog(String archivoLog, long tamanoMaximo) {
        this.archivoLog = archivoLog;
        this.tamanoMaximo = tamanoMaximo;
        this.numeroRotacion = 1;
    }

    //Enum con los tipos de log (arbitrario)
    enum NivelLog{
        INFO,
        WARNING,
        ERROR
    }

    //Metodo para escribir en el log hasta que ocupe su tamaño maximo
    public void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        //Variable que guarda elñ tiempo en el momento en milisegundos
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();

        //Rellena el log
        do {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta + File.separator + archivoLog,  true));
            System.out.println("Log escrito: " + mensaje);
            bw.write(timestamp + " [" + nivel + "] " + mensaje);
            bw.newLine();
            bw.close();
        }while (!rotarSiNecesario());

    }

    //Metodo que comprueba si hace falta rotar al siguiente archivo
    private boolean rotarSiNecesario() throws IOException{
        //Toma el tamaño del archivo en el momento
        long tamanoActual = new File(ruta + File.separator + archivoLog).length();
        File archivo = new File(ruta + File.separator + archivoLog);
        boolean resultado = false;
        //Comprueba si el tamaño actual supera el tamaño maximo, en caso de que sea así cambia su nombre
        if(tamanoActual > tamanoMaximo){
            String nuevoNombre = archivoLog + "." + numeroRotacion;
            File archivoRenombrado = new File(ruta + File.separator + nuevoNombre);
            archivo.renameTo(archivoRenombrado);
            numeroRotacion++;
            System.out.printf("Archivo rotado: " + archivoLog);
            resultado = true;
        }
        return resultado;
    }

    public static void main(String[] args) {
        //Asignamos el nombre base y el tamaño básico
        SistemaLog log = new SistemaLog("app.log", 1024);
        //Utilizamos el try para tratar las IOExeption que lanzan rotarSiNecesario y escribirLog
        try {
            log.escribirLog("Aplicación iniciada", NivelLog.INFO);
            log.escribirLog("Usuario conectado", NivelLog.INFO);
            log.escribirLog("Error de conexión", NivelLog.ERROR);

        }catch (IOException e){
            System.err.println("Error al abrir el archivo" + e.getMessage());
        }
    }
}
