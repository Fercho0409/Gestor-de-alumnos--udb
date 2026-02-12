
package udbvirtual;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    // Archivo de datos (TSV: carnet|nombre)
    private static final Path DATA_FILE = Paths.get("alumnos.tsv");

    // Estructura ordenada por carnet (clave)
    private static final TreeMap<String, Alumno> alumnos = new TreeMap<>();

    public static void main(String[] args) {
        cargarDesdeArchivo();

        // Compatibilidad con JDK 8+: usar el constructor con charset String
        try (Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8)) {
            String opcion;
            do {
                mostrarMenu();
                opcion = sc.nextLine().trim();

                switch (opcion) {
                    case "1":
                        ingresarAlumno(sc);
                        break;
                    case "2":
                        buscarAlumno(sc);
                        break;
                    case "3":
                        eliminarAlumno(sc);
                        break;
                    case "4":
                        mostrarTodos();
                        break;
                    case "5":
                        System.out.println("Saliendo... ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }

                System.out.println();
            } while (!"5".equals(opcion));
        }
    }

    private static void mostrarMenu() {
        System.out.println("========== UDB VIRTUAL - POO ==========");
        System.out.println("1) Ingresar alumno");
        System.out.println("2) Buscar alumno por carnet");
        System.out.println("3) Eliminar alumno por carnet");
        System.out.println("4) Mostrar todos los alumnos (ordenados por carnet)");
        System.out.println("5) Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ingresarAlumno(Scanner sc) {
        System.out.print("Ingrese el carnet: ");
        String carnet = sc.nextLine().trim().toUpperCase();
        if (carnet.isEmpty()) {
            System.out.println("El carnet no puede estar vacío.");
            return;
        }
        if (alumnos.containsKey(carnet)) {
            System.out.println("El carnet ya existe. No se registró el alumno.");
            return;
        }
        System.out.print("Ingrese el nombre completo: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        Alumno alumno = new Alumno(carnet, nombre);
        alumnos.put(carnet, alumno);
        guardarEnArchivo();
        System.out.println("Alumno ingresado exitosamente");
    }

    private static void buscarAlumno(Scanner sc) {
        System.out.print("Ingrese el carnet a buscar: ");
        String carnet = sc.nextLine().trim().toUpperCase();
        Alumno alumno = alumnos.get(carnet);
        if (alumno != null) {
            System.out.println("Alumno encontrado:");
            System.out.println(alumno);
        } else {
            System.out.println("Alumno no encontrado, no se puede Mostrar");
        }
    }

    private static void eliminarAlumno(Scanner sc) {
        System.out.print("Ingrese el carnet a eliminar: ");
        String carnet = sc.nextLine().trim().toUpperCase();
        Alumno eliminado = alumnos.remove(carnet);
        if (eliminado != null) {
            guardarEnArchivo();
            System.out.println("Alumno eliminado exitosamente");
        } else {
            System.out.println("Alumno no encontrado, no se puede Eliminar");
        }
    }

    private static void mostrarTodos() {
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }
        System.out.println("Listado de alumnos (ordenado por carnet):");
        for (Map.Entry<String, Alumno> e : alumnos.entrySet()) {
            System.out.println(e.getValue());
        }
    }

    // =================== Persistencia simple (TSV) ===================

    private static void cargarDesdeArchivo() {
        if (!Files.exists(DATA_FILE)) {
            // Crear archivo con cabecera (compatible con Java 8)
            try (BufferedWriter bw = Files.newBufferedWriter(DATA_FILE, StandardCharsets.UTF_8)) {
                bw.write("carnet|nombre ");
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo de datos: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(DATA_FILE, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank() || linea.startsWith("#") || linea.startsWith("carnet|")) continue;
                String[] partes = linea.split("", 2); // máx 2 campos
                if (partes.length == 2)
                {
                    String carnet = partes[0].trim();
                    String nombre = partes[1].trim();
                    if (!carnet.isEmpty()) {
                        alumnos.put(carnet.toUpperCase(), new Alumno(carnet.toUpperCase(), nombre));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de datos: " + e.getMessage());
        }
    }

    private static void guardarEnArchivo() {
        // Escribimos todo el mapa (ordenado) en el archivo
        try (BufferedWriter bw = Files.newBufferedWriter(DATA_FILE, StandardCharsets.UTF_8)) {
            bw.write("carnet|nombre");
            for (Map.Entry<String, Alumno> e : alumnos.entrySet()) {
                String carnet = e.getKey();
                String nombre;
                nombre = e.getValue().getNombreCompleto().replace(" ", " ").replace("|", "/");

            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de datos: " + e.getMessage());
        }
    }
}
