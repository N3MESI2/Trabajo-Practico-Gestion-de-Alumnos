import java.util.*;

class MenuConsola {
    private final Scanner sc = new Scanner(System.in);
    private final Universidad uni;

    public MenuConsola(Universidad uni) { this.uni = uni; }

    public void iniciar() {
        while (true) {
            mostrarMenu();
            String op = sc.nextLine().trim();
            try {
                switch (op) {
                    case "1": altaAlumno(); break;
                    case "2": matricularAlumno(); break;
                    case "3": inscribirEnMateria(); break;
                    case "4": registrarFalta(); break;
                    case "5": cerrarCursado(); break;
                    case "6": listarAlumnosPorCarreraYMateria(false); break;
                    case "7": listarAlumnosPorCarreraYMateria(true); break;
                    case "8": listarMateriasDeCarrera(); break;
                    case "9": mostrarTablaGeneralInscripciones(); break; // tabla global
                    case "10": uni.limpiarTodo(); System.out.println("Datos borrados."); break;
                    case "0": System.out.println("Saliendo..."); return;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("\nPresione ENTER para continuar...");
            sc.nextLine();
        }
    }

    private void mostrarMenu() {
        System.out.println("=======================================");
        System.out.println("   " + uni.getNombre() + " - Gestión Académica");
        System.out.println("=======================================");
        System.out.println("1) Alta de Alumno");
        System.out.println("2) Matricular Alumno a Carrera");
        System.out.println("3) Inscribir Alumno en Materia");
        System.out.println("4) Registrar Falta (inasistencia) en Materia");
        System.out.println("5) Cargar Situación Final de Materia");
        System.out.println("6) Mostrar Alumnos INSCRIPTOS (por Carrera y Materia)");
        System.out.println("7) Mostrar Alumnos FINALIZADOS (por Carrera y Materia) con situación e inasistencias");
        System.out.println("8) Listar Materias de una Carrera (con cuatrimestre y profesor)");
        System.out.println("9) Tabla general: Carrera | Materia | Alumno | Estado | Inasistencias");
        System.out.println("10) Limpiar todos los datos");
        System.out.println("0) Salir");
        System.out.print("Opción: ");
    }

    // ======== Operaciones ========

    private void altaAlumno() {
        System.out.print("Nombre: "); String nombre = sc.nextLine().trim();
        System.out.print("Apellido: "); String apellido = sc.nextLine().trim();
        System.out.print("DNI: "); String dni = sc.nextLine().trim();
        System.out.print("Legajo (entero): "); int legajo = Integer.parseInt(sc.nextLine().trim());
        if (uni.buscarAlumnoPorLegajo(legajo) != null) {
            System.out.println("Ya existe un alumno con ese legajo."); return;
        }
        Alumno a = new Alumno(nombre, apellido, dni, legajo);
        uni.agregarAlumno(a);
        System.out.println("Alumno agregado: " + a);
    }

    private void matricularAlumno() {
        Alumno a = pedirAlumnoPorLegajo();
        Carrera c = elegirCarrera();
        uni.matricularAlumno(a, c);
        System.out.println("Alumno " + a.getApellido() + " matriculado en " + c.getNombre());
    }

    private void inscribirEnMateria() {
        Alumno a = pedirAlumnoPorLegajo();
        Carrera c = elegirCarrera();
        Materia m = elegirMateriaDe(c);
        uni.inscribirAlumnoEnMateria(a, m);
        System.out.println("Inscripción realizada: " + a.getApellido() + " en " + m.getNombre());
    }

    private void registrarFalta() {
        Alumno a = pedirAlumnoPorLegajo();
        Carrera c = elegirCarrera();
        Materia m = elegirMateriaDe(c);
        uni.registrarFalta(a, m);
        System.out.println("Falta registrada.");
    }

    private void cerrarCursado() {
        Alumno a = pedirAlumnoPorLegajo();
        Carrera c = elegirCarrera();
        Materia m = elegirMateriaDe(c);
        System.out.println("Estados posibles: 1) REGULAR  2) LIBRE  3) PROMOCIONADO");
        System.out.print("Seleccione estado: ");
        int e = Integer.parseInt(sc.nextLine().trim());
        EstadoFinal estado = switch (e) {
            case 1 -> EstadoFinal.REGULAR;
            case 2 -> EstadoFinal.LIBRE;
            case 3 -> EstadoFinal.PROMOCIONADO;
            default -> throw new IllegalArgumentException("Estado inválido.");
        };
        System.out.print("Inasistencias totales: ");
        int faltas = Integer.parseInt(sc.nextLine().trim());
        uni.cerrarCursado(a, m, estado, faltas);
        System.out.println("Cierre de cursado cargado.");
    }

    private void listarAlumnosPorCarreraYMateria(boolean soloFinalizados) {
        Carrera c = elegirCarrera();
        Materia m = elegirMateriaDe(c);
        var insc = uni.obtenerInscripcionesPorEstado(m, soloFinalizados);
        if (insc.isEmpty()) {
            System.out.println("No hay inscripciones para los criterios seleccionados.");
            return;
        }
        for (InscripcionMateria im : insc) {
            if (soloFinalizados) {
                System.out.printf("- %s | Estado: %s | Inasistencias: %d%n",
                        im.getAlumno(), im.getEstadoFinal(), im.getInasistencias());
            } else {
                System.out.printf("- %s%n", im.getAlumno());
            }
        }
    }

    private void listarMateriasDeCarrera() {
        Carrera c = elegirCarrera();
        System.out.println("Materias de " + c.getNombre() + ":");
        for (Materia m : c.getMaterias()) {
            System.out.println(" • " + m + " | Dicta: " + m.getProfesor());
        }
    }

    // ======== Opción 9: tabla global ========

    private void mostrarTablaGeneralInscripciones() {
        var inscripciones = uni.todasLasInscripciones();
        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
            return;
        }

        String h1 = pad("Carrera", 28);
        String h2 = pad("Materia", 28);
        String h3 = pad("Alumno", 36);
        String h4 = pad("Estado", 12);
        String h5 = pad("Inasist.", 9);
        System.out.println(h1 + " | " + h2 + " | " + h3 + " | " + h4 + " | " + h5);
        System.out.println(rep('-', 28) + "-+-" + rep('-', 28) + "-+-" + rep('-', 36) + "-+-" + rep('-', 12) + "-+-" + rep('-', 9));

        for (InscripcionMateria im : inscripciones) {
            String carrera = im.getMateria().getCarrera().getNombre();
            String materia = im.getMateria().getNombre();
            String alumno  = im.getAlumno().toString(); // "Apellido, Nombre (DNI ...) - Legajo X"
            String estado  = im.getEstadoFinal().name();
            String faltas  = String.valueOf(im.getInasistencias());
            System.out.println(
                pad(carrera, 28) + " | " +
                pad(materia, 28) + " | " +
                pad(alumno, 36)  + " | " +
                pad(estado, 12)  + " | " +
                pad(faltas, 9)
            );
        }
    }

    // ======== Selecciones ========

    private Carrera elegirCarrera() {
        List<Carrera> carreras = new ArrayList<>(uni.getCarreras());
        if (carreras.isEmpty()) throw new IllegalStateException("No hay carreras cargadas.");
        for (int i = 0; i < carreras.size(); i++) {
            System.out.println((i + 1) + ") " + carreras.get(i));
        }
        System.out.print("Seleccione carrera: ");
        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        if (idx < 0 || idx >= carreras.size()) throw new IllegalArgumentException("Selección inválida.");
        return carreras.get(idx);
    }

    private Materia elegirMateriaDe(Carrera carrera) {
        List<Materia> materias = carrera.getMaterias();
        if (materias.isEmpty()) throw new IllegalStateException("La carrera no tiene materias cargadas.");
        for (int i = 0; i < materias.size(); i++) {
            System.out.println((i + 1) + ") " + materias.get(i));
        }
        System.out.print("Seleccione materia: ");
        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        if (idx < 0 || idx >= materias.size()) throw new IllegalArgumentException("Selección inválida.");
        return materias.get(idx);
    }

    private Alumno pedirAlumnoPorLegajo() {
        System.out.print("Legajo del alumno: ");
        int legajo = Integer.parseInt(sc.nextLine().trim());
        Alumno a = uni.buscarAlumnoPorLegajo(legajo);
        if (a == null) throw new IllegalArgumentException("No existe alumno con ese legajo.");
        return a;
    }

    // ======== Helpers ========

    private String pad(String s, int n) {
        if (s == null) s = "";
        if (s.length() > n) return s.substring(0, n - 1) + "…";
        return String.format("%-" + n + "s", s);
    }

    private String rep(char ch, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(ch);
        return sb.toString();
    }
}
