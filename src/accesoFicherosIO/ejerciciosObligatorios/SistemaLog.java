package accesoFicherosIO.ejerciciosObligatorios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class SistemaLog {
    private String archivoLog;
    private long tamanoMaximo;
    private int numeroRotacion;
    String ruta = "src/accesoFicherosIO/resourcesApp";

    public SistemaLog(String archivoLog, long tamanoMaximo) {
        this.archivoLog = archivoLog;
        this.tamanoMaximo = tamanoMaximo;
        this.numeroRotacion = 1;
    }

    enum NivelLog{
        INFO,
        WARNING,
        ERROR
    }

    public void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();

        do {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta + File.separator + archivoLog,  true));
            System.out.println("Log escrito: " + mensaje);
            bw.write(timestamp + " [" + nivel + "] " + mensaje);
            bw.newLine();
            bw.close();
        }while (!rotarSiNecesario());

    }

    private boolean rotarSiNecesario() throws IOException{
        long tamanoActual = new File(ruta + File.separator + archivoLog).length();
        File archivo = new File(ruta + File.separator + archivoLog);
        boolean resultado = false;
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
        SistemaLog log = new SistemaLog("app.log", 1024);

        try {
            log.escribirLog("Aplicación iniciada", NivelLog.INFO);
            log.escribirLog("Usuario conectado", NivelLog.INFO);
            log.escribirLog("Error de conexión", NivelLog.ERROR);

        }catch (IOException e){
            System.err.println("Error al abrir el archivo" + e.getMessage());
        }
    }
}
