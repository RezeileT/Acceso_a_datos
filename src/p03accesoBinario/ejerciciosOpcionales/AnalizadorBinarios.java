package p03accesoBinario.ejerciciosOpcionales;

import java.io.*;
import java.util.List;


class ElementoDato {
    private int posicion;
    private String tipo;
    private String valor;

    public ElementoDato(int posicion, String tipo, String valor) {
        this.posicion = posicion;
        this.tipo = tipo;
        this.valor = valor;
    }
}

class Reporte{
    private String nombreArchivo;
    private long tamañoBytes;
    private List<ElementoDato> elementos;
    private int totalInts;
    private int totalDoubles;
    private int totalStrings;

    public Reporte(String nombreArchivo, long tamañoBytes, List<ElementoDato> elementos, int totalInts, int totalDoubles, int totalStrings) {
        this.nombreArchivo = nombreArchivo;
        this.tamañoBytes = tamañoBytes;
        this.elementos = elementos;
        this.totalInts = totalInts;
        this.totalDoubles = totalDoubles;
        this.totalStrings = totalStrings;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "nombreArchivo='" + nombreArchivo + '\'' +
                ", tamañoBytes=" + tamañoBytes +
                ", elementos=" + elementos +
                ", totalInts=" + totalInts +
                ", totalDoubles=" + totalDoubles +
                ", totalStrings=" + totalStrings +
                '}';
    }
}

public class AnalizadorBinarios {

    public static Reporte analizarArchivoBinario(String archivo) throws IOException {
        Reporte reporte = null;
        try(DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {

        }
        return null;
    }

    private static String detectarTipoDato(DataInputStream dis) throws IOException {
        String tipoDato = null;

        return tipoDato;
    }

    public static void guardarReporte(Reporte reporte, String archivo) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(reporte.toString());
        }
    }

    public static void mostrarReporte(Reporte reporte) {

    }

    public static void main(String[] args) {
        String ruta = "src/p03accesoBinario/resources";
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta + File.separator + "datos.dat"))) {
            dos.writeInt(100);
            dos.writeUTF("Producto A");
            dos.writeDouble(99.99);
            dos.writeBoolean(true);
            dos.writeInt(200);
        }catch(IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }

        try{
            Reporte reporte = analizarArchivoBinario("datos.dat");
            mostrarReporte(reporte);
            guardarReporte(reporte, ruta + File.separator + "reporte_datos.txt");
        }catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }

    }

}
