package p03accesoBinario.ejerciciosOpcionales;

import java.io.*;
import java.util.ArrayList;
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

    public int getPosicion() { return posicion; }
    public String getTipo() { return tipo; }
    public String getValor() { return valor; }

    @Override
    public String toString() {
        return "[Pos " + posicion + "] " + tipo + ": " + valor;
    }
}

class Reporte {
    private String nombreArchivo;
    private long tamanoBytes;
    private List<ElementoDato> elementos;
    private int totalInts;
    private int totalDoubles;
    private int totalStrings;
    private int totalBooleans;

    public Reporte(String nombreArchivo, long tamanoBytes, List<ElementoDato> elementos,
                   int totalInts, int totalDoubles, int totalStrings, int totalBooleans) {
        this.nombreArchivo = nombreArchivo;
        this.tamanoBytes = tamanoBytes;
        this.elementos = elementos;
        this.totalInts = totalInts;
        this.totalDoubles = totalDoubles;
        this.totalStrings = totalStrings;
        this.totalBooleans = totalBooleans;
    }

    // Getters
    public String getNombreArchivo() { return nombreArchivo; }
    public long getTamanoBytes() { return tamanoBytes; }
    public List<ElementoDato> getElementos() { return elementos; }
    public int getTotalInts() { return totalInts; }
    public int getTotalDoubles() { return totalDoubles; }
    public int getTotalStrings() { return totalStrings; }
    public int getTotalBooleans() { return totalBooleans; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Reporte de Análisis de Archivo Binario ===\n");
        sb.append("Archivo: ").append(nombreArchivo).append("\n");
        sb.append("Tamaño: ").append(tamanoBytes).append(" bytes\n\n");
        sb.append("Estructura detectada:\n");
        for (ElementoDato e : elementos) {
            sb.append("  ").append(e).append("\n");
        }
        sb.append("\nResumen:\n");
        sb.append("Enteros (int): ").append(totalInts).append("\n");
        sb.append("Decimales (double): ").append(totalDoubles).append("\n");
        sb.append("Cadenas (UTF): ").append(totalStrings).append("\n");
        sb.append("Booleanos: ").append(totalBooleans).append("\n");
        sb.append("Total elementos: ").append(elementos.size()).append("\n");
        return sb.toString();
    }
}

public class AnalizadorBinarios {

    public static Reporte analizarArchivoBinario(String archivo) throws IOException {
        File f = new File(archivo);
        if (!f.exists()) {
            throw new FileNotFoundException("Archivo no encontrado: " + archivo);
        }

        long tamano = f.length();
        List<ElementoDato> elementos = new ArrayList<>();
        int totalInts = 0, totalDoubles = 0, totalStrings = 0, totalBooleans = 0;

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
             DataInputStream dis = new DataInputStream(bis)) {

            int pos = 0;

            while (pos < tamano) {
                // Verificar bytes restantes
                int bytesRestantes = (int)(tamano - pos);

                // Detectar tipo solo si quedan bytes suficientes para leer al menos 1 byte
                if (bytesRestantes < 1) break;

                String tipo = detectarTipoDato(dis, bytesRestantes);
                ElementoDato elemento = null;

                switch (tipo) {
                    case "INT":
                        if (bytesRestantes < 4) throw new EOFException();
                        int i = dis.readInt();
                        elemento = new ElementoDato(pos, "INT", String.valueOf(i));
                        totalInts++;
                        pos += 4;
                        break;

                    case "UTF":
                        if (bytesRestantes < 2) throw new EOFException();
                        // leer primero la longitud sin consumir el stream para asegurar que no hay EOF
                        dis.mark(2);
                        int lenUTF = dis.readUnsignedShort();
                        dis.reset();

                        if (bytesRestantes < 2 + lenUTF) throw new EOFException();

                        String s = dis.readUTF();
                        elemento = new ElementoDato(pos, "UTF", "\"" + s + "\"");
                        totalStrings++;
                        pos += 2 + s.getBytes("UTF-8").length;
                        break;

                    case "DOUBLE":
                        if (bytesRestantes < 8) throw new EOFException();
                        double d = dis.readDouble();
                        elemento = new ElementoDato(pos, "DOUBLE", String.valueOf(d));
                        totalDoubles++;
                        pos += 8;
                        break;

                    case "BOOLEAN":
                        if (bytesRestantes < 1) throw new EOFException();
                        boolean b = dis.readBoolean();
                        elemento = new ElementoDato(pos, "BOOLEAN", String.valueOf(b));
                        totalBooleans++;
                        pos += 1;
                        break;

                    default:
                        throw new IOException("Tipo no reconocido en posición " + pos + ": " + tipo);
                }

                if (elemento != null) {
                    elementos.add(elemento);
                }
            }
        } catch (EOFException eof) {
            // Fin del archivo alcanzado de forma controlada
        }

        return new Reporte(f.getName(), tamano, elementos, totalInts, totalDoubles, totalStrings, totalBooleans);
    }

    /**
     * Detecta el tipo de dato SIN el error mark/reset usando BufferedInputStream
     */
    private static String detectarTipoDato(DataInputStream dis, int bytesRestantes) throws IOException {
        dis.mark(8);
        byte[] buffer = new byte[Math.min(8, bytesRestantes)];
        int bytesLeidos = dis.read(buffer);
        dis.reset();

        if (bytesLeidos >= 2) {
            int lenUTF = ((buffer[0] & 0xFF) << 8) | (buffer[1] & 0xFF);
            if (lenUTF > 0 && lenUTF <= 65535) return "UTF";
        }

        if (bytesLeidos >= 1) {
            if (buffer[0] == 0x00 || buffer[0] == 0x01) return "BOOLEAN";
        }

        if (bytesLeidos >= 4) {
            int candidatoInt = ((buffer[0] & 0xFF) << 24) | ((buffer[1] & 0xFF) << 16) |
                    ((buffer[2] & 0xFF) << 8) | (buffer[3] & 0xFF);
            if (Math.abs(candidatoInt) < 2_000_000_000L) return "INT";
        }

        if (bytesLeidos >= 8) {
            int byte6 = buffer[6] & 0xFF;
            int byte7 = buffer[7] & 0xFF;
            if ((byte6 >= 0x3F && byte6 <= 0xBF) || (byte7 >= 0x3F && byte7 <= 0xBF)) return "DOUBLE";
        }
        return "INT";
    }

    public static void guardarReporte(Reporte reporte, String archivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(reporte.toString());
        }
    }

    public static void mostrarReporte(Reporte reporte) {
        System.out.println(reporte.toString());
    }

    public static void main(String[] args) {
        String ruta = "src/p03accesoBinario/resources";
        File dir = new File(ruta);
        if (!dir.exists()) dir.mkdirs();

        String binario = ruta + File.separator + "datos.dat";

        // Crear archivo de prueba
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(binario))) {
            dos.writeInt(100);
            dos.writeUTF("Producto A");
            dos.writeDouble(99.99);
            dos.writeBoolean(true);
            dos.writeInt(200);
            System.out.println("✓ Archivo 'datos.dat' creado correctamente");
        } catch (IOException e) {
            System.err.println("Error al crear archivo: " + e.getMessage());
            return;
        }

        // Analizar
        try {
            Reporte reporte = analizarArchivoBinario(binario);
            mostrarReporte(reporte);
            guardarReporte(reporte, ruta + File.separator + "reporte_datos.txt");
            System.out.println("\n✓ Reporte guardado en 'reporte_datos.txt'");
        } catch (IOException e) {
            System.err.println("Error en análisis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
